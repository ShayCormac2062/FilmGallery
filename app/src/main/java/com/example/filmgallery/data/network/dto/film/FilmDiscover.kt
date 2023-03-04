package com.example.filmgallery.data.network.dto.film

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FilmDiscover(
    val page: Int,
    val results: List<FilmDto>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int
)
