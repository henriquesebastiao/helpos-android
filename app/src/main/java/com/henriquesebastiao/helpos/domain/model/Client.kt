package com.henriquesebastiao.helpos.domain.model

import java.time.Instant

data class Client(
    val id: Long,
    val ixcId: Long,
    val name: String,
    val login: String,
    val pppoePassword: String,
    val location: String,
    val cto: String?,
    val port: String?,
    val address: String,
    val reference: String?,
    val city: String?,
    val createdAt: Instant,
    val updatedAt: Instant,
)
