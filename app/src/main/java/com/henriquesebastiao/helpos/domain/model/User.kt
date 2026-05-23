package com.henriquesebastiao.helpos.domain.model

data class User(
    val id: Long,
    val username: String,
    val isActive: Boolean,
)
