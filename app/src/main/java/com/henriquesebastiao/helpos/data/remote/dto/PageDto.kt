package com.henriquesebastiao.helpos.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PageDto<T>(
    val items: List<T>,
    val total: Int,
    val limit: Int,
    val offset: Int,
)
