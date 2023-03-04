package com.example.filmgallery.di

import com.example.filmgallery.data.local.db.dao.FilmDao
import com.example.filmgallery.data.local.db.dao.UserDao
import com.example.filmgallery.data.local.db.database.FilmDatabase
import com.example.filmgallery.data.local.db.database.UserDatabase
import com.example.filmgallery.data.network.api.Api
import com.example.filmgallery.data.repository.FilmsRepositoryImpl
import com.example.filmgallery.data.repository.LocalFilmsRepositoryImpl
import com.example.filmgallery.data.repository.SerialsRepositoryImpl
import com.example.filmgallery.data.repository.UsersRepositoryImpl
import com.example.filmgallery.domain.repository.FilmsRepository
import com.example.filmgallery.domain.repository.LocalFilmsRepository
import com.example.filmgallery.domain.repository.SerialsRepository
import com.example.filmgallery.domain.repository.UsersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepoModule {

    @Provides
    fun provideFilmsRepository(
        api: Api
    ): FilmsRepository = FilmsRepositoryImpl(api)


    @Provides
    fun provideSerialsRepository(
        api: Api
    ): SerialsRepository = SerialsRepositoryImpl(api)

    @Provides
    fun provideUsersRepository(
        dao: UserDao
    ): UsersRepository = UsersRepositoryImpl(dao)

    @Provides
    fun provideLocalFilmsRepository(
        dao: FilmDao
    ): LocalFilmsRepository = LocalFilmsRepositoryImpl(dao)

    @Provides
    fun provideFilmDao(
        database: FilmDatabase
    ): FilmDao = database.filmDao()

    @Provides
    fun provideUserDao(
        database: UserDatabase
    ): UserDao = database.userDao()

}
