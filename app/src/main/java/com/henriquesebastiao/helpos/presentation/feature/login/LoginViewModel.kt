package com.henriquesebastiao.helpos.presentation.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.henriquesebastiao.helpos.core.util.ApiResult
import com.henriquesebastiao.helpos.core.util.DomainError
import com.henriquesebastiao.helpos.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val showPassword: Boolean = false,
    val loading: Boolean = false,
    val errorMessage: String? = null,
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onUsernameChange(value: String) {
        _uiState.update { it.copy(username = value, errorMessage = null) }
    }

    fun onPasswordChange(value: String) {
        _uiState.update { it.copy(password = value, errorMessage = null) }
    }

    fun togglePasswordVisibility() {
        _uiState.update { it.copy(showPassword = !it.showPassword) }
    }

    fun submit() {
        val state = _uiState.value
        if (state.username.isBlank() || state.password.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Preencha usuário e senha.") }
            return
        }
        _uiState.update { it.copy(loading = true, errorMessage = null) }
        viewModelScope.launch {
            val result = authRepository.login(state.username.trim(), state.password)
            _uiState.update { it.copy(loading = false) }
            if (result is ApiResult.Failure) {
                _uiState.update { it.copy(errorMessage = describe(result.error)) }
            }
        }
    }

    private fun describe(error: DomainError): String = when (error) {
        DomainError.NoConnection -> "Sem conexão. Verifique a URL do backend e a rede."
        DomainError.Timeout -> "Tempo esgotado ao falar com o backend."
        DomainError.Unauthorized -> "Usuário ou senha inválidos."
        DomainError.NotFound -> "Endpoint de login não encontrado. Confira a URL do backend."
        is DomainError.ServerError -> "Erro do servidor (HTTP ${error.code})."
        is DomainError.Parsing -> "Resposta do servidor não pôde ser interpretada."
        is DomainError.Unknown -> "Erro inesperado: ${error.cause.message ?: "sem detalhes"}"
    }
}
