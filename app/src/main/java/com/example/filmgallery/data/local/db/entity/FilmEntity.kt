package com.example.filmgallery.data.local.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.filmgallery.domain.model.Film
import com.example.filmgallery.domain.model.Genre

@Entity(tableName = "films")
data class FilmEntity(
    @PrimaryKey
    @ColumnInfo(name = "film_id")
    val filmId: Int,
    val adult: Boolean,
    val path: String,
    val genres: List<String> = emptyList(),
    val language: String,
    @ColumnInfo(name = "original_Title")
    val originalTitle: String,
    val review: String,
    @ColumnInfo(name = "popularity_grade")
    val popularityGrade: Double,
    @ColumnInfo(name = "poster_url")
    val posterUrl: String,
    @ColumnInfo(name = "release_date")
    val releaseDate: String,
    val title: String,
    @ColumnInfo(name = "vote_averages")
    val voteAverages: Double,
    @ColumnInfo(name = "vote_counts")
    val voteCounts: Int,
    val status: String,
    @ColumnInfo(name = "user_name")
    var userName: String = "",
    val runtime: Int
) {
    fun toFilm() = Film(
        adult,
        path,
        genres.map { Genre(0, it) },
        filmId,
        language,
        originalTitle,
        review,
        popularityGrade,
        posterUrl,
        releaseDate,
        title,
        voteAverages,
        voteCounts,
        status
    )
}
