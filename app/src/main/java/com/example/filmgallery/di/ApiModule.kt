package com.example.filmgallery.di

import com.example.filmgallery.data.network.api.Api
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    fun provideApi(
        @Named("retrofit") retrofit: Retrofit
    ): Api = retrofit.create(Api::class.java)

}
