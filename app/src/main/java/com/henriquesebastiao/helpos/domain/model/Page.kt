package com.henriquesebastiao.helpos.domain.model

data class Page<T>(
    val items: List<T>,
    val total: Int,
    val limit: Int,
    val offset: Int,
) {
    val hasMore: Boolean get() = offset + items.size < total
    val nextOffset: Int get() = offset + items.size
}
