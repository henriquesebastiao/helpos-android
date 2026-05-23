package com.henriquesebastiao.helpos.domain.usecase.client

import com.henriquesebastiao.helpos.core.util.ApiResult
import com.henriquesebastiao.helpos.data.mapper.ClientForm
import com.henriquesebastiao.helpos.domain.model.Client
import com.henriquesebastiao.helpos.domain.repository.ClientRepository
import javax.inject.Inject

class UpdateClientUseCase @Inject constructor(
    private val repository: ClientRepository,
) {
    suspend operator fun invoke(clientId: Long, form: ClientForm): ApiResult<Client> =
        repository.update(clientId, form)
}
