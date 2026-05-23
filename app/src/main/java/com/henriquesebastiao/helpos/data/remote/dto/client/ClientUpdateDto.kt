package com.henriquesebastiao.helpos.data.remote.dto.client

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ClientUpdateDto(
    @SerialName("ixc_id") val ixcId: Long? = null,
    val name: String? = null,
    val login: String? = null,
    @SerialName("pppoe_password") val pppoePassword: String? = null,
    val location: String? = null,
    val cto: String? = null,
    val port: String? = null,
    val address: String? = null,
    val reference: String? = null,
    val city: String? = null,
)
