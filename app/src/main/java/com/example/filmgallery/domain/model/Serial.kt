package com.example.filmgallery.domain.model

data class Serial(
    val path: String,
    val firstAirDate: String,
    val genres: List<Genre>,
    val serialId: Int,
    val title: String,
    val originCountry: List<String>,
    val language: String,
    val originalTitle: String,
    val review: String,
    val popularityGrade: Double,
    val posterUrl: String,
    val voteAverages: Double,
    val voteCounts: Int,
    val status: String = "Serial",
    val runtime: Int
) : BaseElement(
    posterPath = posterUrl,
    name = title,
    id = serialId,
    voteAverage = voteAverages,
    label = status
) {
    fun toFilm() = Film(
        false,
        path,
        genres,
        serialId,
        language,
        originalTitle,
        review,
        popularityGrade,
        posterUrl,
        firstAirDate,
        title,
        voteAverages,
        voteCounts,
        status,
        runtime
    )
}
