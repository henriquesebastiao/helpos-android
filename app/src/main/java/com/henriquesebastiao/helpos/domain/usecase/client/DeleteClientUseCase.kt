package com.henriquesebastiao.helpos.domain.usecase.client

import com.henriquesebastiao.helpos.core.util.ApiResult
import com.henriquesebastiao.helpos.domain.repository.ClientRepository
import javax.inject.Inject

class DeleteClientUseCase @Inject constructor(
    private val repository: ClientRepository,
) {
    suspend operator fun invoke(clientId: Long): ApiResult<Unit> = repository.delete(clientId)
}
