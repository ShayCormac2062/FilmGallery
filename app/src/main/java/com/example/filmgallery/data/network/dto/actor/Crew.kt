package com.example.filmgallery.data.network.dto.actor

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Crew(
    val adult: Boolean,
    @SerialName("credit_id")
    val creditId: String,
    val department: String,
    val gender: Int,
    val id: Int,
    val job: String,
    @SerialName("known_for_department")
    val known_for_department: String,
    val name: String,
    @SerialName("original_name")
    val originalName: String,
    val popularity: Double,
    @SerialName("profile_path")
    val profilePath: String = ""
)
