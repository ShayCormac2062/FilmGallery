package com.example.filmgallery.data.network.dto.serial

import com.example.filmgallery.data.network.dto.GenreDto
import com.example.filmgallery.domain.model.Serial
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SerialDto(
    @SerialName("backdrop_path")
    val backdropPath: String? = null,
    @SerialName("first_air_date")
    val firstAirDate: String = "",
    @SerialName("genres")
    val genres: List<GenreDto> = emptyList(),
    val id: Int,
    val name: String = "",
    @SerialName("origin_country")
    val originCountry: List<String>,
    @SerialName("episode_run_time")
    val episodeRunTime: List<Int> = listOf(30),
    @SerialName("original_language")
    val originalLanguage: String = "",
    @SerialName("original_name")
    val originalName: String = "",
    val overview: String = "",
    val popularity: Double,
    @SerialName("poster_path")
    val posterPath: String = "",
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("vote_count")
    val voteCount: Int,
) {
    fun toSerial() = Serial(
        path = backdropPath.toString(),
        firstAirDate = firstAirDate,
        genres = genres.map { it.toGenre() },
        serialId = id,
        language = originalLanguage,
        originalTitle = originalName,
        review = overview,
        popularityGrade = popularity,
        posterUrl = posterPath,
        title = name,
        voteAverages = voteAverage,
        voteCounts = voteCount,
        originCountry = originCountry,
        runtime = episodeRunTime[0]
    )
}
