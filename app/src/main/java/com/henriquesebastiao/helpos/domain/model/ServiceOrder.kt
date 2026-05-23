package com.henriquesebastiao.helpos.domain.model

import java.time.Instant
import java.time.LocalDate

data class ServiceOrder(
    val id: Long,
    val ixcId: Long,
    val clientId: Long,
    val subject: String,
    val date: LocalDate,
    val report: String,
    val createdAt: Instant,
    val updatedAt: Instant,
)
