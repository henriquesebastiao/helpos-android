package com.henriquesebastiao.helpos.domain.model

data class ClientWithServiceOrders(
    val client: Client,
    val serviceOrders: List<ServiceOrder>,
)
