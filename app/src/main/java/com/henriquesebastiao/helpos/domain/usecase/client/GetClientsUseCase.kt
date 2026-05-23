package com.henriquesebastiao.helpos.domain.usecase.client

import com.henriquesebastiao.helpos.core.util.ApiResult
import com.henriquesebastiao.helpos.domain.model.Client
import com.henriquesebastiao.helpos.domain.model.Page
import com.henriquesebastiao.helpos.domain.repository.ClientFilters
import com.henriquesebastiao.helpos.domain.repository.ClientRepository
import javax.inject.Inject

class GetClientsUseCase @Inject constructor(
    private val repository: ClientRepository,
) {
    suspend operator fun invoke(
        filters: ClientFilters = ClientFilters(),
        limit: Int = DEFAULT_LIMIT,
        offset: Int = 0,
    ): ApiResult<Page<Client>> = repository.list(filters, limit, offset)

    companion object {
        const val DEFAULT_LIMIT = 50
    }
}
