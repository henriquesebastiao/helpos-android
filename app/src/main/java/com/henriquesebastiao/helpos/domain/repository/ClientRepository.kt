package com.henriquesebastiao.helpos.domain.repository

import com.henriquesebastiao.helpos.core.util.ApiResult
import com.henriquesebastiao.helpos.data.mapper.ClientForm
import com.henriquesebastiao.helpos.domain.model.Client
import com.henriquesebastiao.helpos.domain.model.ClientWithServiceOrders
import com.henriquesebastiao.helpos.domain.model.Page
import com.henriquesebastiao.helpos.domain.model.ServiceOrder

data class ClientFilters(
    val name: String? = null,
    val login: String? = null,
    val ixcId: Long? = null,
    val cto: String? = null,
    val city: String? = null,
)

interface ClientRepository {
    suspend fun list(filters: ClientFilters, limit: Int, offset: Int): ApiResult<Page<Client>>
    suspend fun detail(clientId: Long): ApiResult<ClientWithServiceOrders>
    suspend fun byIxc(ixcId: Long): ApiResult<ClientWithServiceOrders>
    suspend fun create(form: ClientForm): ApiResult<Client>
    suspend fun update(clientId: Long, form: ClientForm): ApiResult<Client>
    suspend fun delete(clientId: Long): ApiResult<Unit>
    suspend fun listServiceOrders(clientId: Long, limit: Int, offset: Int): ApiResult<Page<ServiceOrder>>
}
