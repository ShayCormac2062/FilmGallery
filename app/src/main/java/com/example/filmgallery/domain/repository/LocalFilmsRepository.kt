package com.example.filmgallery.domain.repository

import com.example.filmgallery.domain.model.Film

interface LocalFilmsRepository {

    suspend fun addFilm(film: Film, userName: String)
    suspend fun getFilms(userName: String): List<Film>?
    suspend fun getFilmById(id: Int, userName: String): Film?
    suspend fun deleteFilm(id: Int, userName: String)

}
