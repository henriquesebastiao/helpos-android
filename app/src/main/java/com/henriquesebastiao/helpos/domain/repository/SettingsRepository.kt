package com.henriquesebastiao.helpos.domain.repository

import com.henriquesebastiao.helpos.domain.model.AppSettings
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun observe(): Flow<AppSettings>
    fun current(): AppSettings
    fun updateBackendUrl(url: String)
    fun updateClaudeApiKey(key: String)
}
