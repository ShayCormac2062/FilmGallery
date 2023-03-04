package com.example.filmgallery.data.repository

import com.example.filmgallery.data.local.db.dao.FilmDao
import com.example.filmgallery.domain.model.Film
import com.example.filmgallery.domain.repository.LocalFilmsRepository
import javax.inject.Inject

class LocalFilmsRepositoryImpl @Inject constructor(
    private val dao: FilmDao,
) : LocalFilmsRepository {

    override suspend fun addFilm(film: Film, userName: String) =
        dao.add(film.toFilmEntity().apply {
            this.userName = userName
        })

    override suspend fun getFilms(userName: String): List<Film>? =
        dao.getFilms(userName)?.map { it.toFilm() }

    override suspend fun getFilmById(id: Int, userName: String): Film? =
        dao.getFilmById(id, userName)?.toFilm()

    override suspend fun deleteFilm(id: Int, userName: String) =
        dao.deleteFilm(id, userName)
}
