package com.henriquesebastiao.helpos.data.repository

import com.henriquesebastiao.helpos.core.security.SecurePrefs
import com.henriquesebastiao.helpos.domain.model.AppSettings
import com.henriquesebastiao.helpos.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepositoryImpl @Inject constructor(
    private val prefs: SecurePrefs,
) : SettingsRepository {

    override fun observe(): Flow<AppSettings> =
        prefs.observeChanges().map { current() }

    override fun current(): AppSettings = AppSettings(
        backendUrl = prefs.getString(SecurePrefs.KEY_BACKEND_URL, AppSettings.DEFAULT_BACKEND_URL),
        claudeApiKey = prefs.getString(SecurePrefs.KEY_CLAUDE_API_KEY),
    )

    override fun updateBackendUrl(url: String) {
        prefs.putString(SecurePrefs.KEY_BACKEND_URL, url.trim())
    }

    override fun updateClaudeApiKey(key: String) {
        prefs.putString(SecurePrefs.KEY_CLAUDE_API_KEY, key.trim())
    }
}
