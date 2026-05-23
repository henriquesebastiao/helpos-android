package com.henriquesebastiao.helpos.domain.model

data class AppSettings(
    val backendUrl: String,
    val claudeApiKey: String,
) {
    companion object {
        const val DEFAULT_BACKEND_URL = "http://172.16.0.10:8000/"
        val EMPTY = AppSettings(
            backendUrl = DEFAULT_BACKEND_URL,
            claudeApiKey = "",
        )
    }
}
