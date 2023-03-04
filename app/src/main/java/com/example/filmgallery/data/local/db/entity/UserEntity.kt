package com.example.filmgallery.data.local.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.filmgallery.domain.model.User

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    val userId: Int = 0,
    @ColumnInfo(name = "first_name")
    val firstName: String,
    @ColumnInfo(name = "last_name")
    val lastName: String,
    @ColumnInfo(name = "user_name")
    val userName: String,
    @ColumnInfo(name = "image_url")
    val imageUrl: String,
    @ColumnInfo(name = "password_text")
    val password: String,
    @ColumnInfo(name = "is_entered")
    val isEntered: Boolean
) {
    fun toUser() = User(
        userId,
        firstName,
        lastName,
        userName,
        imageUrl,
        password,
        isEntered
    )
}
