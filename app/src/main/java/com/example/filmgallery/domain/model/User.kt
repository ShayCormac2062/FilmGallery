package com.example.filmgallery.domain.model

import com.example.filmgallery.data.local.db.entity.UserEntity

data class User(
    var userId: Int = 0,
    var firstName: String,
    var lastName: String,
    var userName: String,
    var imageUrl: String,
    var password: String,
    var isEntered: Boolean
) {
    fun toUserEntity() = UserEntity(
        userId,
        firstName,
        lastName,
        userName,
        imageUrl,
        password,
        isEntered
    )
}
