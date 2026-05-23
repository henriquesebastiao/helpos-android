package com.henriquesebastiao.helpos.core.network

import com.henriquesebastiao.helpos.core.security.SecurePrefs
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiKeyInterceptor @Inject constructor(
    private val prefs: SecurePrefs,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val key = prefs.getString(SecurePrefs.KEY_HELPOS_API_KEY)
        val request = if (key.isNotBlank()) {
            chain.request().newBuilder()
                .addHeader(HEADER_NAME, key)
                .build()
        } else {
            chain.request()
        }
        return chain.proceed(request)
    }

    companion object {
        const val HEADER_NAME = "X-API-Key"
    }
}
