package com.henriquesebastiao.helpos.data.remote.dto.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RefreshRequestDto(
    @SerialName("refresh_token") val refreshToken: String,
)
