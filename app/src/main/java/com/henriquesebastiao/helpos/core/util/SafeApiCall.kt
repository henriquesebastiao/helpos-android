package com.henriquesebastiao.helpos.core.util

import kotlinx.serialization.SerializationException
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import java.net.SocketTimeoutException

suspend inline fun <T> safeApiCall(crossinline block: suspend () -> T): ApiResult<T> = try {
    ApiResult.Success(block())
} catch (e: HttpException) {
    val error = when (val code = e.code()) {
        401, 403 -> DomainError.Unauthorized
        404 -> DomainError.NotFound
        else -> DomainError.ServerError(code, e.message())
    }
    ApiResult.Failure(error)
} catch (e: SocketTimeoutException) {
    ApiResult.Failure(DomainError.Timeout)
} catch (e: IOException) {
    ApiResult.Failure(DomainError.NoConnection)
} catch (e: SerializationException) {
    Timber.e(e, "Falha de desserialização na resposta da API")
    ApiResult.Failure(DomainError.Parsing(e))
} catch (e: Throwable) {
    Timber.e(e, "Erro inesperado em chamada à API")
    ApiResult.Failure(DomainError.Unknown(e))
}
