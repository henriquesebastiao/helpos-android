package com.henriquesebastiao.helpos.data.mapper

import com.henriquesebastiao.helpos.data.remote.dto.PageDto
import com.henriquesebastiao.helpos.data.remote.dto.client.ClientCreateDto
import com.henriquesebastiao.helpos.data.remote.dto.client.ClientReadDetailedDto
import com.henriquesebastiao.helpos.data.remote.dto.client.ClientReadDto
import com.henriquesebastiao.helpos.data.remote.dto.client.ClientUpdateDto
import com.henriquesebastiao.helpos.domain.model.Client
import com.henriquesebastiao.helpos.domain.model.ClientWithServiceOrders
import com.henriquesebastiao.helpos.domain.model.Page
import java.time.Instant

fun ClientReadDto.toDomain(): Client = Client(
    id = id,
    ixcId = ixcId,
    name = name,
    login = login,
    pppoePassword = pppoePassword,
    location = location,
    cto = cto,
    port = port,
    address = address,
    reference = reference,
    city = city,
    createdAt = Instant.parse(createdAt),
    updatedAt = Instant.parse(updatedAt),
)

fun ClientReadDetailedDto.toDomain(): ClientWithServiceOrders = ClientWithServiceOrders(
    client = Client(
        id = id,
        ixcId = ixcId,
        name = name,
        login = login,
        pppoePassword = pppoePassword,
        location = location,
        cto = cto,
        port = port,
        address = address,
        reference = reference,
        city = city,
        createdAt = Instant.parse(createdAt),
        updatedAt = Instant.parse(updatedAt),
    ),
    serviceOrders = serviceOrders.map { it.toDomain() },
)

fun <D, M> PageDto<D>.toDomain(map: (D) -> M): Page<M> = Page(
    items = items.map(map),
    total = total,
    limit = limit,
    offset = offset,
)

data class ClientForm(
    val name: String,
    val login: String,
    val pppoePassword: String,
    val location: String,
    val address: String,
    val ixcId: Long,
    val cto: String? = null,
    val port: String? = null,
    val reference: String? = null,
    val city: String? = null,
)

fun ClientForm.toCreateDto(): ClientCreateDto = ClientCreateDto(
    ixcId = ixcId,
    name = name,
    login = login,
    pppoePassword = pppoePassword,
    location = location,
    address = address,
    cto = cto,
    port = port,
    reference = reference,
    city = city,
)

fun ClientForm.toUpdateDto(): ClientUpdateDto = ClientUpdateDto(
    ixcId = ixcId,
    name = name,
    login = login,
    pppoePassword = pppoePassword,
    location = location,
    cto = cto,
    port = port,
    address = address,
    reference = reference,
    city = city,
)
