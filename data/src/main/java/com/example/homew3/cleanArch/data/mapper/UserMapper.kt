package com.example.homew3.cleanArch.data.mapper

import com.example.homew3.cleanArch.data.model.entity.UserEntity
import com.example.homew3.domain.model.User


internal fun UserEntity.toDomain(): User {
    return User(
        id = id,
        login = login,
        password = password,
        email = email,
        shopList = shopList,
        favorite = favorite.toListDomain()
    )
}

internal fun User.toEntity(): UserEntity {
    return UserEntity(
        id = id,
        login = login,
        password = password,
        email = email,
        shopList = shopList,
        favorite = favorite.toListRecipeEntity()
    )
}

