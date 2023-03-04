package com.example.filmgallery.data.network.dto.actor

import kotlinx.serialization.Serializable

@Serializable
data class CreditsDiscover(
    val cast: List<Cast>,
    val crew: List<Crew>,
    val id: Int
)
