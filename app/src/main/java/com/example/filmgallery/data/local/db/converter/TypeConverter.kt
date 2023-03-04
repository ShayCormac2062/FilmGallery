package com.example.filmgallery.data.local.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson

class TypeConverter {

    @TypeConverter
    fun fromString(value: String) = Gson().fromJson(value, Array<String>::class.java).toList()

    @TypeConverter
    fun fromList(list: List<String>) = Gson().toJson(list)

}
