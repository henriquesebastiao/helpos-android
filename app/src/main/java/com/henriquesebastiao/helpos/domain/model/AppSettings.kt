package com.henriquesebastiao.helpos.domain.model

data class AppSettings(
    val backendUrl: String,
    val helpOsApiKey: String,
    val claudeApiKey: String,
) {
    val isConfigured: Boolean
        get() = backendUrl.isNotBlank() && helpOsApiKey.isNotBlank()

    companion object {
        const val DEFAULT_BACKEND_URL = "http://172.16.0.10:8000/"
        val EMPTY = AppSettings(
            backendUrl = DEFAULT_BACKEND_URL,
            helpOsApiKey = "",
            claudeApiKey = "",
        )
    }
}
