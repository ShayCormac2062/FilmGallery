package com.example.filmgallery.data.repository

import com.example.filmgallery.data.network.api.Api
import com.example.filmgallery.domain.model.Actor
import com.example.filmgallery.domain.model.Film
import com.example.filmgallery.domain.repository.FilmsRepository
import com.example.filmgallery.utils.ApplicationException
import com.example.filmgallery.utils.RequestResult
import javax.inject.Inject

class FilmsRepositoryImpl @Inject constructor(
    private val api: Api
) : FilmsRepository {

    override suspend fun getFilms(
        language: String,
        sortBy: String,
        includeAdult: Boolean,
        includeVideo: Boolean,
        page: Int,
        monetization: String
    ): RequestResult<List<Film>> = try {
        with(
            api.getMovies(
                language, sortBy, includeAdult, includeVideo, page, monetization
            )
        ) {
            if (isSuccessful && body() != null) {
                RequestResult.Success(
                    data = body()?.results?.map { it.toFilm() }
                )
            } else {
                RequestResult.Error(
                    message = ApplicationException.ApiException().message,
                    data = emptyList()
                )
            }
        }
    } catch (ex: Throwable) {
        RequestResult.Error(
            message = if (ex.message == "Connection reset") {
                ApplicationException.VPNException().message
            } else ApplicationException.InternetException().message
        )
    }

    override suspend fun getFilmsBySearch(
        language: String,
        query: String,
        includeAdult: Boolean
    ): RequestResult<List<Film>> = try {
        with(
            api.getMoviesBySearch(
                language, query, includeAdult,
            )
        ) {
            if (isSuccessful && body()?.results?.isNotEmpty() == true) {
                RequestResult.Success(
                    data = body()?.results?.map { it.toFilm() }
                )
            } else {
                RequestResult.Success(
                    data = emptyList()
                )
            }
        }
    } catch (ex: Throwable) {
        RequestResult.Error(
            message = if (ex.message == "Connection reset") {
                ApplicationException.VPNException().message
            } else ApplicationException.InternetException().message
        )
    }

    override suspend fun getFilmById(id: Int, language: String): RequestResult<Film> = try {
        with(api.getMovieById(id, language)) {
            if (isSuccessful && body() != null) {
                RequestResult.Success(
                    data = body()?.toFilm()
                )
            } else RequestResult.Error(
                message = ApplicationException.ApiException().message,
            )
        }
    } catch (ex: Throwable) {
        RequestResult.Error(
            message = if (ex.message == "Connection reset") {
                ApplicationException.VPNException().message
            } else ApplicationException.InternetException().message
        )
    }

    override suspend fun getFilmCreditsById(
        id: Int,
        language: String
    ): RequestResult<List<Actor>> = try {
        with(api.getFilmCreditsById(id, language)) {
            if (isSuccessful && body() != null) {
                RequestResult.Success(
                    data = body()?.cast?.map { it.toActor() }
                )
            } else RequestResult.Error(
                message = ApplicationException.ApiException().message,
            )
        }
    } catch (ex: Throwable) {
        RequestResult.Error(
            message = if (ex.message == "Connection reset") {
                ApplicationException.VPNException().message
            } else ApplicationException.InternetException().message
        )
    }

}
