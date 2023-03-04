package com.example.filmgallery.data.network.dto

import com.example.filmgallery.domain.model.Genre
import kotlinx.serialization.Serializable

@Serializable
data class GenreDto(
    val id: Int = 0,
    val name: String = ""
) {
    fun toGenre() = Genre(
        id = id,
        name = name
    )
}
