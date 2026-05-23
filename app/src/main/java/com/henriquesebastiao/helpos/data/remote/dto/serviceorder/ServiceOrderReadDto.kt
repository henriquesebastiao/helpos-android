package com.henriquesebastiao.helpos.data.remote.dto.serviceorder

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ServiceOrderReadDto(
    val id: Long,
    @SerialName("ixc_id") val ixcId: Long,
    @SerialName("client_id") val clientId: Long,
    val subject: String,
    val date: String,
    val report: String,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String,
)
