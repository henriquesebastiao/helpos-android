package com.henriquesebastiao.helpos.data.mapper

import com.henriquesebastiao.helpos.data.remote.dto.auth.UserReadDto
import com.henriquesebastiao.helpos.domain.model.User

fun UserReadDto.toDomain(): User = User(
    id = id,
    username = username,
    isActive = isActive,
)
