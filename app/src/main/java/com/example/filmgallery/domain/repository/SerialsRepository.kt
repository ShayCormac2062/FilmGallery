package com.example.filmgallery.domain.repository

import com.example.filmgallery.domain.model.Actor
import com.example.filmgallery.domain.model.Serial
import com.example.filmgallery.utils.RequestResult

interface SerialsRepository {

    suspend fun getSerials(
        language: String,
        sortBy: String,
        page: Int,
        timezone: String,
        includeNullFirstAirDates: Boolean,
        monetization: String,
        withStatus: Int,
        withType: Int,
    ): RequestResult<List<Serial>>

    suspend fun getSerialsBySearch(
        language: String,
        query: String,
        includeAdult: Boolean,
    ): RequestResult<List<Serial>>

    suspend fun getSerialById(
        id: Int,
        language: String
    ): RequestResult<Serial>

    suspend fun getSerialCreditsById(
        id: Int,
        language: String = "en-US"
    ): RequestResult<List<Actor>>
}
