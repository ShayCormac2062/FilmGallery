package com.example.filmgallery.domain.model

import com.example.filmgallery.data.local.db.entity.FilmEntity

data class Film(
    val adult: Boolean,
    val path: String,
    val genres: List<Genre>,
    val filmId: Int,
    val language: String,
    val originalTitle: String,
    val review: String,
    val popularityGrade: Double,
    val posterUrl: String,
    val releaseDate: String,
    val title: String,
    val voteAverages: Double,
    val voteCounts: Int,
    val status: String = "Film",
    val runtime: Int = 90
) : BaseElement(
    id = filmId,
    posterPath = posterUrl,
    name = title,
    voteAverage = voteAverages,
    label = status
) {
    fun toFilmEntity() = FilmEntity(
        filmId,
        adult,
        path,
        genres.map { it.name },
        language,
        originalTitle,
        review,
        popularityGrade,
        posterUrl,
        releaseDate,
        title,
        voteAverages,
        voteCounts,
        status,
        runtime = runtime
    )

    fun toSerial() = Serial(
        path,
        releaseDate,
        genres,
        filmId,
        title,
        emptyList(),
        language,
        originalTitle,
        review,
        popularityGrade,
        posterUrl,
        voteAverages,
        voteCounts,
        runtime = runtime
    )
}
