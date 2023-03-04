package com.example.filmgallery.data.network.dto.actor

import com.example.filmgallery.domain.model.Actor
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Cast(
    val adult: Boolean,
    val character: String,
    val credit_id: String,
    val gender: Int,
    val id: Int,
    @SerialName("known_for_department")
    val knownForDepartment: String,
    val name: String,
    val order: Int,
    @SerialName("original_name")
    val originalName: String,
    val popularity: Double,
    @SerialName("profile_path")
    val profilePath: String = ""
) {
    fun toActor() = Actor(
        name,
        profilePath
    )
}
