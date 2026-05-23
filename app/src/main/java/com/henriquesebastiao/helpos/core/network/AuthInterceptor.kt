package com.henriquesebastiao.helpos.core.network

import com.henriquesebastiao.helpos.core.security.SecurePrefs
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val prefs: SecurePrefs,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        if (original.url.encodedPath.contains(AUTH_LOGIN_PATH) ||
            original.url.encodedPath.contains(AUTH_REFRESH_PATH)
        ) {
            return chain.proceed(original)
        }
        val token = prefs.getString(SecurePrefs.KEY_ACCESS_TOKEN)
        val request = if (token.isNotBlank()) {
            original.newBuilder()
                .header(HEADER_AUTHORIZATION, "$BEARER_PREFIX$token")
                .build()
        } else {
            original
        }
        return chain.proceed(request)
    }

    companion object {
        const val HEADER_AUTHORIZATION = "Authorization"
        const val BEARER_PREFIX = "Bearer "
        const val AUTH_LOGIN_PATH = "/api/v1/auth/login"
        const val AUTH_REFRESH_PATH = "/api/v1/auth/refresh"
    }
}
