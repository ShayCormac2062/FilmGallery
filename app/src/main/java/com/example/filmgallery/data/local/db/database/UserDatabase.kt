package com.example.filmgallery.data.local.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.filmgallery.data.local.db.dao.UserDao
import com.example.filmgallery.data.local.db.entity.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = true
)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
