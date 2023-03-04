package com.example.filmgallery.data.local.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.filmgallery.data.local.db.converter.TypeConverter
import com.example.filmgallery.data.local.db.dao.FilmDao
import com.example.filmgallery.data.local.db.entity.FilmEntity

@Database(
    entities = [FilmEntity::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(TypeConverter::class)
abstract class FilmDatabase : RoomDatabase() {
    abstract fun filmDao(): FilmDao
}
