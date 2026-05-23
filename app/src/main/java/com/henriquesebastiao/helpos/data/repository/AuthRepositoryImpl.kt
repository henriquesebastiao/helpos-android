package com.henriquesebastiao.helpos.data.repository

import com.henriquesebastiao.helpos.core.security.SecurePrefs
import com.henriquesebastiao.helpos.core.util.ApiResult
import com.henriquesebastiao.helpos.core.util.map
import com.henriquesebastiao.helpos.core.util.onSuccess
import com.henriquesebastiao.helpos.core.util.safeApiCall
import com.henriquesebastiao.helpos.data.mapper.toDomain
import com.henriquesebastiao.helpos.data.remote.api.AuthApi
import com.henriquesebastiao.helpos.data.remote.dto.auth.LoginRequestDto
import com.henriquesebastiao.helpos.domain.model.SessionState
import com.henriquesebastiao.helpos.domain.model.User
import com.henriquesebastiao.helpos.domain.repository.AuthRepository
import dagger.Lazy
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val prefs: SecurePrefs,
    private val authApi: Lazy<AuthApi>,
) : AuthRepository {

    private val _sessionState = MutableStateFlow<SessionState>(initialState())
    override val sessionState: StateFlow<SessionState> = _sessionState.asStateFlow()

    private fun initialState(): SessionState {
        val username = prefs.getString(SecurePrefs.KEY_USERNAME)
        val accessToken = prefs.getString(SecurePrefs.KEY_ACCESS_TOKEN)
        return if (username.isNotBlank() && accessToken.isNotBlank()) {
            SessionState.LoggedIn(username)
        } else {
            SessionState.LoggedOut
        }
    }

    override suspend fun login(username: String, password: String): ApiResult<User> {
        val tokenResult = safeApiCall {
            authApi.get().login(LoginRequestDto(username = username, password = password))
        }
        val tokens = when (tokenResult) {
            is ApiResult.Success -> tokenResult.data
            is ApiResult.Failure -> return ApiResult.Failure(tokenResult.error)
        }
        prefs.putString(SecurePrefs.KEY_ACCESS_TOKEN, tokens.accessToken)
        prefs.putString(SecurePrefs.KEY_REFRESH_TOKEN, tokens.refreshToken)

        return safeApiCall { authApi.get().me() }
            .map { it.toDomain() }
            .onSuccess { user ->
                prefs.putString(SecurePrefs.KEY_USERNAME, user.username)
                _sessionState.value = SessionState.LoggedIn(user.username)
            }
    }

    override suspend fun refreshUser(): ApiResult<User> =
        safeApiCall { authApi.get().me() }
            .map { it.toDomain() }
            .onSuccess { user ->
                prefs.putString(SecurePrefs.KEY_USERNAME, user.username)
                _sessionState.value = SessionState.LoggedIn(user.username)
            }

    override fun logout() {
        prefs.clearTokens()
        _sessionState.value = SessionState.LoggedOut
    }
}
