package com.example.filmgallery.data.network.api

import com.example.filmgallery.data.network.dto.actor.CreditsDiscover
import com.example.filmgallery.data.network.dto.film.FilmDiscover
import com.example.filmgallery.data.network.dto.film.FilmDto
import com.example.filmgallery.data.network.dto.serial.SerialDiscover
import com.example.filmgallery.data.network.dto.serial.SerialDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("discover/movie")
    suspend fun getMovies(
        @Query("language") language: String,
        @Query("sort_by") sortBy: String,
        @Query("include_adult") includeAdult: Boolean,
        @Query("include_video") includeVideo: Boolean,
        @Query("page") page: Int,
        @Query("with_watch_monetization_types") monetization: String
    ) : Response<FilmDiscover>

    @GET("search/movie")
    suspend fun getMoviesBySearch(
        @Query("language") language: String,
        @Query("query") query: String,
        @Query("include_adult") includeAdult: Boolean,
    ) : Response<FilmDiscover>

    @GET("movie/{movie_id}")
    suspend fun getMovieById(
        @Path("movie_id") id: Int,
        @Query("language") language: String
    ) : Response<FilmDto>

    @GET("movie/{movie_id}/credits")
    suspend fun getFilmCreditsById(
        @Path("movie_id") id: Int,
        @Query("language") language: String
    ) : Response<CreditsDiscover>

    @GET("discover/tv")
    suspend fun getSerials(
        @Query("language") language: String,
        @Query("sort_by") sortBy: String,
        @Query("page") page: Int,
        @Query("timezone") timezone: String,
        @Query("include_null_first_air_dates") includeNullFirstAirDates: Boolean,
        @Query("with_watch_monetization_types") monetization: String,
        @Query("with_status") withStatus: Int,
        @Query("with_type") withType: Int,
    ) : Response<SerialDiscover>

    @GET("search/tv")
    suspend fun getSerialsBySearch(
        @Query("language") language: String,
        @Query("query") query: String,
        @Query("include_adult") includeAdult: Boolean,
    ) : Response<SerialDiscover>

    @GET("tv/{tv_id}")
    suspend fun getSerialById(
        @Path("tv_id") id: Int,
        @Query("language") language: String
    ) : Response<SerialDto>

    @GET("tv/{tv_id}/credits")
    suspend fun getSerialCreditsById(
        @Path("tv_id") id: Int,
        @Query("language") language: String
    ) : Response<CreditsDiscover>

}
