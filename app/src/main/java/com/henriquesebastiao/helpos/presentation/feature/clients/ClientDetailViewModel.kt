package com.henriquesebastiao.helpos.presentation.feature.clients

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.henriquesebastiao.helpos.core.util.ApiResult
import com.henriquesebastiao.helpos.core.util.DomainError
import com.henriquesebastiao.helpos.domain.model.Client
import com.henriquesebastiao.helpos.domain.model.ServiceOrder
import com.henriquesebastiao.helpos.domain.usecase.client.GetClientDetailUseCase
import com.henriquesebastiao.helpos.presentation.navigation.ClientDetailRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ClientDetailUiState(
    val loading: Boolean = true,
    val client: Client? = null,
    val serviceOrders: List<ServiceOrder> = emptyList(),
    val errorMessage: String? = null,
)

@HiltViewModel
class ClientDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getDetail: GetClientDetailUseCase,
) : ViewModel() {

    private val route = savedStateHandle.toRoute<ClientDetailRoute>()
    val clientId: Long = route.clientId

    private val _uiState = MutableStateFlow(ClientDetailUiState())
    val uiState: StateFlow<ClientDetailUiState> = _uiState.asStateFlow()

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            _uiState.update { it.copy(loading = true, errorMessage = null) }
            when (val result = getDetail(clientId)) {
                is ApiResult.Success -> _uiState.update {
                    it.copy(
                        loading = false,
                        client = result.data.client,
                        serviceOrders = result.data.serviceOrders,
                        errorMessage = null,
                    )
                }
                is ApiResult.Failure -> _uiState.update {
                    it.copy(
                        loading = false,
                        errorMessage = describe(result.error),
                    )
                }
            }
        }
    }

    private fun describe(error: DomainError): String = when (error) {
        DomainError.NoConnection -> "Sem conexão. Verifique a URL do backend e a rede."
        DomainError.Timeout -> "Tempo esgotado ao consultar o backend."
        DomainError.Unauthorized -> "Sessão expirada. Faça login novamente."
        DomainError.NotFound -> "Cliente não encontrado."
        is DomainError.ServerError -> "Erro do servidor (HTTP ${error.code})."
        is DomainError.Parsing -> "Resposta do servidor não pôde ser interpretada."
        is DomainError.Unknown -> "Erro inesperado: ${error.cause.message ?: "sem detalhes"}"
    }
}
