package com.henriquesebastiao.helpos.domain.usecase.client

import com.henriquesebastiao.helpos.core.util.ApiResult
import com.henriquesebastiao.helpos.domain.model.ClientWithServiceOrders
import com.henriquesebastiao.helpos.domain.repository.ClientRepository
import javax.inject.Inject

class GetClientDetailUseCase @Inject constructor(
    private val repository: ClientRepository,
) {
    suspend operator fun invoke(clientId: Long): ApiResult<ClientWithServiceOrders> =
        repository.detail(clientId)
}
