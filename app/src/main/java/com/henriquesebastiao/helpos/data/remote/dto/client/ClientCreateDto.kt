package com.henriquesebastiao.helpos.data.remote.dto.client

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ClientCreateDto(
    @SerialName("ixc_id") val ixcId: Long,
    val name: String,
    val login: String,
    @SerialName("pppoe_password") val pppoePassword: String,
    val location: String,
    val address: String,
    val cto: String? = null,
    val port: String? = null,
    val reference: String? = null,
    val city: String? = null,
)
