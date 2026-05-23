package com.henriquesebastiao.helpos.data.remote.dto.client

import com.henriquesebastiao.helpos.data.remote.dto.serviceorder.ServiceOrderReadDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ClientReadDetailedDto(
    val id: Long,
    @SerialName("ixc_id") val ixcId: Long,
    val name: String,
    val login: String,
    @SerialName("pppoe_password") val pppoePassword: String,
    val location: String,
    val cto: String? = null,
    val port: String? = null,
    val address: String,
    val reference: String? = null,
    val city: String? = null,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String,
    @SerialName("service_orders") val serviceOrders: List<ServiceOrderReadDto> = emptyList(),
)
