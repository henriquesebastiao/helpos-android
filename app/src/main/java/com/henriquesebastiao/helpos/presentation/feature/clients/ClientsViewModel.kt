package com.henriquesebastiao.helpos.presentation.feature.clients

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.henriquesebastiao.helpos.core.util.ApiResult
import com.henriquesebastiao.helpos.core.util.DomainError
import com.henriquesebastiao.helpos.domain.model.Client
import com.henriquesebastiao.helpos.domain.repository.ClientFilters
import com.henriquesebastiao.helpos.domain.usecase.client.GetClientsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ClientsUiState(
    val query: String = "",
    val clients: List<Client> = emptyList(),
    val loadingInitial: Boolean = false,
    val loadingMore: Boolean = false,
    val refreshing: Boolean = false,
    val errorMessage: String? = null,
    val total: Int = 0,
    val hasMore: Boolean = false,
    val nextOffset: Int = 0,
)

@OptIn(FlowPreview::class)
@HiltViewModel
class ClientsViewModel @Inject constructor(
    private val getClients: GetClientsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ClientsUiState())
    val uiState: StateFlow<ClientsUiState> = _uiState.asStateFlow()

    private val queryFlow = MutableStateFlow("")

    init {
        viewModelScope.launch {
            queryFlow
                .debounce(DEBOUNCE_MILLIS)
                .distinctUntilChanged()
                .collect { query -> loadFirstPage(query) }
        }
    }

    fun onQueryChange(value: String) {
        _uiState.update { it.copy(query = value) }
        queryFlow.value = value
    }

    fun retry() {
        viewModelScope.launch { loadFirstPage(_uiState.value.query) }
    }

    fun refresh() {
        viewModelScope.launch {
            _uiState.update { it.copy(refreshing = true) }
            loadFirstPage(_uiState.value.query)
            _uiState.update { it.copy(refreshing = false) }
        }
    }

    fun loadMore() {
        val state = _uiState.value
        if (state.loadingMore || state.loadingInitial || !state.hasMore) return
        viewModelScope.launch {
            _uiState.update { it.copy(loadingMore = true) }
            when (val result = fetch(state.query, state.nextOffset)) {
                is ApiResult.Success -> _uiState.update {
                    it.copy(
                        clients = it.clients + result.data.items,
                        loadingMore = false,
                        hasMore = result.data.hasMore,
                        nextOffset = result.data.nextOffset,
                        total = result.data.total,
                    )
                }
                is ApiResult.Failure -> _uiState.update {
                    it.copy(loadingMore = false, errorMessage = describe(result.error))
                }
            }
        }
    }

    private suspend fun loadFirstPage(query: String) {
        _uiState.update { it.copy(loadingInitial = true, errorMessage = null) }
        when (val result = fetch(query, offset = 0)) {
            is ApiResult.Success -> _uiState.update {
                it.copy(
                    clients = result.data.items,
                    loadingInitial = false,
                    total = result.data.total,
                    hasMore = result.data.hasMore,
                    nextOffset = result.data.nextOffset,
                    errorMessage = null,
                )
            }
            is ApiResult.Failure -> _uiState.update {
                it.copy(
                    loadingInitial = false,
                    clients = emptyList(),
                    errorMessage = describe(result.error),
                )
            }
        }
    }

    private suspend fun fetch(query: String, offset: Int) = getClients(
        filters = ClientFilters(name = query.takeIf { it.isNotBlank() }),
        offset = offset,
    )

    private fun describe(error: DomainError): String = when (error) {
        DomainError.NoConnection -> "Sem conexão. Verifique a URL do backend e a rede."
        DomainError.Timeout -> "Tempo esgotado ao consultar o backend."
        DomainError.Unauthorized -> "Sessão expirada. Faça login novamente."
        DomainError.NotFound -> "Endpoint de clientes não encontrado."
        is DomainError.ServerError -> "Erro do servidor (HTTP ${error.code})."
        is DomainError.Parsing -> "Resposta do servidor não pôde ser interpretada."
        is DomainError.Unknown -> "Erro inesperado: ${error.cause.message ?: "sem detalhes"}"
    }

    companion object {
        private const val DEBOUNCE_MILLIS = 300L
    }
}
