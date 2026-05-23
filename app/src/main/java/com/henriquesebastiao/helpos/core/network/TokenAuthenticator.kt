package com.henriquesebastiao.helpos.core.network

import com.henriquesebastiao.helpos.core.security.SecurePrefs
import com.henriquesebastiao.helpos.data.remote.dto.auth.RefreshRequestDto
import com.henriquesebastiao.helpos.data.remote.dto.auth.TokenPairDto
import com.henriquesebastiao.helpos.domain.model.AppSettings
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.Authenticator
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.Route
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenAuthenticator @Inject constructor(
    private val prefs: SecurePrefs,
    private val json: Json,
) : Authenticator {

    private val refreshClient: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(REFRESH_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .readTimeout(REFRESH_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .writeTimeout(REFRESH_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .build()

    override fun authenticate(route: Route?, response: Response): Request? = synchronized(this) {
        val originalAuth = response.request.header(AuthInterceptor.HEADER_AUTHORIZATION) ?: return null
        if (priorResponseCount(response) >= MAX_RETRIES) return null

        val refreshToken = prefs.getString(SecurePrefs.KEY_REFRESH_TOKEN)
        if (refreshToken.isBlank()) return null

        val currentAccess = prefs.getString(SecurePrefs.KEY_ACCESS_TOKEN)
        val expected = "${AuthInterceptor.BEARER_PREFIX}$currentAccess"
        if (currentAccess.isNotBlank() && expected != originalAuth) {
            return response.request.newBuilder()
                .header(AuthInterceptor.HEADER_AUTHORIZATION, expected)
                .build()
        }

        val newTokens = refreshTokens(refreshToken)
        if (newTokens == null) {
            prefs.clearTokens()
            return null
        }
        prefs.putString(SecurePrefs.KEY_ACCESS_TOKEN, newTokens.accessToken)
        prefs.putString(SecurePrefs.KEY_REFRESH_TOKEN, newTokens.refreshToken)

        return response.request.newBuilder()
            .header(AuthInterceptor.HEADER_AUTHORIZATION, "${AuthInterceptor.BEARER_PREFIX}${newTokens.accessToken}")
            .build()
    }

    private fun refreshTokens(refreshToken: String): TokenPairDto? {
        val baseUrl = prefs.getString(SecurePrefs.KEY_BACKEND_URL, AppSettings.DEFAULT_BACKEND_URL)
            .trim()
            .toHttpUrlOrNull() ?: return null
        val url = baseUrl.newBuilder()
            .encodedPath("/api/v1/auth/refresh")
            .build()
        val body = json.encodeToString(RefreshRequestDto(refreshToken = refreshToken))
            .toRequestBody(JSON_MEDIA_TYPE.toMediaType())
        val request = Request.Builder().url(url).post(body).build()
        return try {
            refreshClient.newCall(request).execute().use { resp ->
                if (!resp.isSuccessful) {
                    Timber.w("Refresh falhou com HTTP ${resp.code}")
                    null
                } else {
                    val payload = resp.body?.string().orEmpty()
                    json.decodeFromString<TokenPairDto>(payload)
                }
            }
        } catch (t: Throwable) {
            Timber.e(t, "Erro ao chamar /auth/refresh")
            null
        }
    }

    private fun priorResponseCount(response: Response): Int {
        var count = 0
        var current: Response? = response.priorResponse
        while (current != null) {
            count++
            current = current.priorResponse
        }
        return count
    }

    companion object {
        private const val MAX_RETRIES = 1
        private const val REFRESH_TIMEOUT_SECONDS = 15L
        private const val JSON_MEDIA_TYPE = "application/json"
    }
}
