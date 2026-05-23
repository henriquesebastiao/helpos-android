package com.henriquesebastiao.helpos.core.util

sealed class DomainError(open val cause: Throwable? = null) {
    data object NoConnection : DomainError()
    data object Timeout : DomainError()
    data object Unauthorized : DomainError()
    data object NotFound : DomainError()
    data class ServerError(val code: Int, val message: String?) : DomainError()
    data class Parsing(override val cause: Throwable) : DomainError(cause)
    data class Unknown(override val cause: Throwable) : DomainError(cause)
}
