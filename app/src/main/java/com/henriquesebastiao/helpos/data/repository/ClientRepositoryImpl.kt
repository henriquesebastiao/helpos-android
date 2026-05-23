package com.henriquesebastiao.helpos.data.repository

import com.henriquesebastiao.helpos.core.util.ApiResult
import com.henriquesebastiao.helpos.core.util.map
import com.henriquesebastiao.helpos.core.util.safeApiCall
import com.henriquesebastiao.helpos.data.mapper.ClientForm
import com.henriquesebastiao.helpos.data.mapper.toCreateDto
import com.henriquesebastiao.helpos.data.mapper.toDomain
import com.henriquesebastiao.helpos.data.mapper.toUpdateDto
import com.henriquesebastiao.helpos.data.remote.api.ClientApi
import com.henriquesebastiao.helpos.domain.model.Client
import com.henriquesebastiao.helpos.domain.model.ClientWithServiceOrders
import com.henriquesebastiao.helpos.domain.model.Page
import com.henriquesebastiao.helpos.domain.model.ServiceOrder
import com.henriquesebastiao.helpos.domain.repository.ClientFilters
import com.henriquesebastiao.helpos.domain.repository.ClientRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClientRepositoryImpl @Inject constructor(
    private val api: ClientApi,
) : ClientRepository {

    override suspend fun list(
        filters: ClientFilters,
        limit: Int,
        offset: Int,
    ): ApiResult<Page<Client>> = safeApiCall {
        api.list(
            name = filters.name?.takeIf { it.isNotBlank() },
            login = filters.login?.takeIf { it.isNotBlank() },
            ixcId = filters.ixcId,
            cto = filters.cto?.takeIf { it.isNotBlank() },
            city = filters.city?.takeIf { it.isNotBlank() },
            limit = limit,
            offset = offset,
        )
    }.map { page -> page.toDomain { it.toDomain() } }

    override suspend fun detail(clientId: Long): ApiResult<ClientWithServiceOrders> =
        safeApiCall { api.get(clientId) }.map { it.toDomain() }

    override suspend fun byIxc(ixcId: Long): ApiResult<ClientWithServiceOrders> =
        safeApiCall { api.getByIxc(ixcId) }.map { it.toDomain() }

    override suspend fun create(form: ClientForm): ApiResult<Client> =
        safeApiCall { api.create(form.toCreateDto()) }.map { it.toDomain() }

    override suspend fun update(clientId: Long, form: ClientForm): ApiResult<Client> =
        safeApiCall { api.update(clientId, form.toUpdateDto()) }.map { it.toDomain() }

    override suspend fun delete(clientId: Long): ApiResult<Unit> =
        safeApiCall { api.delete(clientId) }

    override suspend fun listServiceOrders(
        clientId: Long,
        limit: Int,
        offset: Int,
    ): ApiResult<Page<ServiceOrder>> = safeApiCall {
        api.listServiceOrders(clientId, limit, offset)
    }.map { page -> page.toDomain { it.toDomain() } }
}
