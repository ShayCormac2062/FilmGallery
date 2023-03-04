package com.example.filmgallery.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.filmgallery.data.local.db.entity.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: UserEntity)

    @Query("SELECT * FROM users WHERE user_name = :userName and password_text = :password")
    fun loginUser(userName: String, password: String): UserEntity?

    @Query("SELECT * FROM users WHERE is_entered")
    fun getUser(): UserEntity?

    @Query("SELECT user_name FROM users WHERE is_entered LIMIT 1")
    fun getUserName(): String?

    @Query("UPDATE users SET is_entered = :isEntered WHERE user_id = :userId")
    fun quit(userId: Int, isEntered: Boolean)

}
