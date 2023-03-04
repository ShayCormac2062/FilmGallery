package com.example.filmgallery.di

import android.content.Context
import androidx.room.Room
import com.example.filmgallery.data.local.db.database.FilmDatabase
import com.example.filmgallery.data.local.db.database.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideFilmDb(
        @ApplicationContext context: Context
    ): FilmDatabase = Room
        .databaseBuilder(context, FilmDatabase::class.java, FILMS_DATABASE_NAME)
        .build()

    @Singleton
    @Provides
    fun provideUserDb(
        @ApplicationContext context: Context
    ): UserDatabase = Room
        .databaseBuilder(context, UserDatabase::class.java, USER_DATABASE_NAME)
        .build()

    companion object {
        private const val FILMS_DATABASE_NAME = "films"
        private const val USER_DATABASE_NAME = "users"
    }

}
