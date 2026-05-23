package com.henriquesebastiao.helpos.domain.repository

import com.henriquesebastiao.helpos.core.util.ApiResult
import com.henriquesebastiao.helpos.domain.model.SessionState
import com.henriquesebastiao.helpos.domain.model.User
import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {
    val sessionState: StateFlow<SessionState>
    suspend fun login(username: String, password: String): ApiResult<User>
    suspend fun refreshUser(): ApiResult<User>
    fun logout()
}
