package com.henriquesebastiao.helpos.data.remote.api

import com.henriquesebastiao.helpos.data.remote.dto.auth.LoginRequestDto
import com.henriquesebastiao.helpos.data.remote.dto.auth.RefreshRequestDto
import com.henriquesebastiao.helpos.data.remote.dto.auth.TokenPairDto
import com.henriquesebastiao.helpos.data.remote.dto.auth.UserReadDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {
    @POST("api/v1/auth/login/json")
    suspend fun login(@Body request: LoginRequestDto): TokenPairDto

    @POST("api/v1/auth/refresh")
    suspend fun refresh(@Body request: RefreshRequestDto): TokenPairDto

    @GET("api/v1/auth/me")
    suspend fun me(): UserReadDto
}
