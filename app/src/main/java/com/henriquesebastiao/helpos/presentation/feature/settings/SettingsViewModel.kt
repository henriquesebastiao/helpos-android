package com.henriquesebastiao.helpos.presentation.feature.settings

import androidx.lifecycle.ViewModel
import com.henriquesebastiao.helpos.core.util.UrlValidator
import com.henriquesebastiao.helpos.domain.model.AppSettings
import com.henriquesebastiao.helpos.domain.repository.AuthRepository
import com.henriquesebastiao.helpos.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class SettingsUiState(
    val backendUrl: String = AppSettings.DEFAULT_BACKEND_URL,
    val claudeApiKey: String = "",
    val showClaudeKey: Boolean = false,
    val backendUrlError: String? = null,
    val saved: Boolean = false,
    val initialized: Boolean = false,
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val draft = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = draft.asStateFlow()

    init {
        val current = settingsRepository.current()
        draft.update {
            it.copy(
                backendUrl = current.backendUrl.ifBlank { AppSettings.DEFAULT_BACKEND_URL },
                claudeApiKey = current.claudeApiKey,
                initialized = true,
            )
        }
    }

    fun onBackendUrlChange(value: String) {
        draft.update { it.copy(backendUrl = value, backendUrlError = null, saved = false) }
    }

    fun onClaudeApiKeyChange(value: String) {
        draft.update { it.copy(claudeApiKey = value, saved = false) }
    }

    fun toggleClaudeKeyVisibility() {
        draft.update { it.copy(showClaudeKey = !it.showClaudeKey) }
    }

    fun save(): Boolean {
        val state = draft.value
        if (!UrlValidator.isValid(state.backendUrl)) {
            draft.update { it.copy(backendUrlError = "URL inválida") }
            return false
        }
        settingsRepository.updateBackendUrl(state.backendUrl)
        settingsRepository.updateClaudeApiKey(state.claudeApiKey)
        draft.update { it.copy(saved = true) }
        return true
    }

    fun logout() {
        authRepository.logout()
    }
}
