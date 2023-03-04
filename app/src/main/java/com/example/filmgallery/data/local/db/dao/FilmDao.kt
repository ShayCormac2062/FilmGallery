package com.example.filmgallery.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.filmgallery.data.local.db.entity.FilmEntity

@Dao
interface FilmDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(name: FilmEntity)

    @Query("SELECT * FROM films WHERE user_name = :userName")
    fun getFilms(userName: String): List<FilmEntity>?

    @Query("SELECT * FROM films WHERE film_id = :filmId AND user_name = :userName")
    fun getFilmById(filmId: Int, userName: String): FilmEntity?

    @Query("DELETE FROM films WHERE film_id = :filmId AND user_name = :userName")
    fun deleteFilm(filmId: Int, userName: String)

}
