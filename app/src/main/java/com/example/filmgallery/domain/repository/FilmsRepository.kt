package com.example.filmgallery.domain.repository

import com.example.filmgallery.domain.model.Actor
import com.example.filmgallery.domain.model.Film
import com.example.filmgallery.utils.RequestResult

interface FilmsRepository {

    suspend fun getFilms(
        language: String,
        sortBy: String,
        includeAdult: Boolean,
        includeVideo: Boolean,
        page: Int,
        monetization: String
    ): RequestResult<List<Film>>

    suspend fun getFilmsBySearch(
        language: String,
        query: String,
        includeAdult: Boolean,
    ): RequestResult<List<Film>>

    suspend fun getFilmById(
        id: Int,
        language: String = "en-US"
    ): RequestResult<Film>

    suspend fun getFilmCreditsById(
        id: Int,
        language: String = "en-US"
    ): RequestResult<List<Actor>>

}
