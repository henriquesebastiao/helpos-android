package com.henriquesebastiao.helpos.presentation.feature.settings

import androidx.lifecycle.ViewModel
import com.henriquesebastiao.helpos.core.util.UrlValidator
import com.henriquesebastiao.helpos.domain.model.AppSettings
import com.henriquesebastiao.helpos.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class SettingsUiState(
    val backendUrl: String = AppSettings.DEFAULT_BACKEND_URL,
    val helpOsApiKey: String = "",
    val claudeApiKey: String = "",
    val showHelpOsKey: Boolean = false,
    val showClaudeKey: Boolean = false,
    val backendUrlError: String? = null,
    val saved: Boolean = false,
    val initialized: Boolean = false,
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: SettingsRepository,
) : ViewModel() {

    private val draft = MutableStateFlow(SettingsUiState())

    val uiState: StateFlow<SettingsUiState> = draft.asStateFlow()

    init {
        val current = repository.current()
        draft.update {
            it.copy(
                backendUrl = current.backendUrl.ifBlank { AppSettings.DEFAULT_BACKEND_URL },
                helpOsApiKey = current.helpOsApiKey,
                claudeApiKey = current.claudeApiKey,
                initialized = true,
            )
        }
    }

    fun onBackendUrlChange(value: String) {
        draft.update { it.copy(backendUrl = value, backendUrlError = null, saved = false) }
    }

    fun onHelpOsApiKeyChange(value: String) {
        draft.update { it.copy(helpOsApiKey = value, saved = false) }
    }

    fun onClaudeApiKeyChange(value: String) {
        draft.update { it.copy(claudeApiKey = value, saved = false) }
    }

    fun toggleHelpOsKeyVisibility() {
        draft.update { it.copy(showHelpOsKey = !it.showHelpOsKey) }
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
        repository.updateBackendUrl(state.backendUrl)
        repository.updateHelpOsApiKey(state.helpOsApiKey)
        repository.updateClaudeApiKey(state.claudeApiKey)
        draft.update { it.copy(saved = true) }
        return true
    }
}
