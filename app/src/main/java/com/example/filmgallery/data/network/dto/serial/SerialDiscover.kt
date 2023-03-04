package com.example.filmgallery.data.network.dto.serial

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SerialDiscover(
    val page: Int,
    val results: List<SerialDto>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int
)
