package com.henriquesebastiao.helpos.data.mapper

import com.henriquesebastiao.helpos.data.remote.dto.serviceorder.ServiceOrderReadDto
import com.henriquesebastiao.helpos.domain.model.ServiceOrder
import java.time.Instant
import java.time.LocalDate

fun ServiceOrderReadDto.toDomain(): ServiceOrder = ServiceOrder(
    id = id,
    ixcId = ixcId,
    clientId = clientId,
    subject = subject,
    date = LocalDate.parse(date),
    report = report,
    createdAt = Instant.parse(createdAt),
    updatedAt = Instant.parse(updatedAt),
)
