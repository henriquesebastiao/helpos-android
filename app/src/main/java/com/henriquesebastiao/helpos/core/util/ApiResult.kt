package com.henriquesebastiao.helpos.core.util

sealed interface ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>
    data class Failure(val error: DomainError) : ApiResult<Nothing>
}

inline fun <T, R> ApiResult<T>.map(transform: (T) -> R): ApiResult<R> = when (this) {
    is ApiResult.Success -> ApiResult.Success(transform(data))
    is ApiResult.Failure -> this
}

inline fun <T> ApiResult<T>.onSuccess(block: (T) -> Unit): ApiResult<T> = also {
    if (this is ApiResult.Success) block(data)
}

inline fun <T> ApiResult<T>.onFailure(block: (DomainError) -> Unit): ApiResult<T> = also {
    if (this is ApiResult.Failure) block(error)
}
