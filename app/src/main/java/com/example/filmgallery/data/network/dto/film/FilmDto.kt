package com.example.filmgallery.data.network.dto.film

import com.example.filmgallery.data.network.dto.GenreDto
import com.example.filmgallery.domain.model.Film
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FilmDto(
    val adult: Boolean = false,
    @SerialName("backdrop_path")
    val backdropPath: String = "",
    @SerialName("genres")
    val genres: List<GenreDto> = emptyList(),
    val id: Int,
    @SerialName("original_language")
    val originalLanguage: String = "",
    @SerialName("original_title")
    val originalTitle: String = "",
    val overview: String = "",
    val popularity: Double,
    @SerialName("poster_path")
    val posterPath: String = "",
    @SerialName("release_date")
    val releaseDate: String = "",
    val title: String = "",
    val video: Boolean,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("vote_count")
    val voteCount: Int,
    val runtime: Int = 90
) {
    fun toFilm() = Film(
        adult = adult,
        path = backdropPath,
        genres = genres.map { it.toGenre() },
        filmId = id,
        language = originalLanguage,
        originalTitle = originalTitle,
        review = overview,
        popularityGrade = popularity,
        posterUrl = posterPath,
        releaseDate = releaseDate,
        title = title,
        voteAverages = voteAverage,
        voteCounts = voteCount,
        runtime = runtime
    )
}
