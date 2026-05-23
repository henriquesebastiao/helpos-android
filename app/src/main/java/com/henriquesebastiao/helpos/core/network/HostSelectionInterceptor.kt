package com.henriquesebastiao.helpos.core.network

import com.henriquesebastiao.helpos.core.security.SecurePrefs
import com.henriquesebastiao.helpos.domain.model.AppSettings
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HostSelectionInterceptor @Inject constructor(
    private val prefs: SecurePrefs,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val rawUrl = prefs.getString(
            SecurePrefs.KEY_BACKEND_URL,
            AppSettings.DEFAULT_BACKEND_URL,
        ).trim()
        val target = rawUrl.toHttpUrlOrNull() ?: return chain.proceed(chain.request())

        val request = chain.request()
        val rewritten = request.url.newBuilder()
            .scheme(target.scheme)
            .host(target.host)
            .port(target.port)
            .build()
        return chain.proceed(request.newBuilder().url(rewritten).build())
    }
}
