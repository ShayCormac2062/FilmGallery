package com.example.filmgallery.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class NetModule {

    companion object {
        private const val URL = "https://api.themoviedb.org/3/"
        private const val API_KEY = "02b113b496621e5a49428c55c55a3ccc"
        private const val QUERY_PARAM = "api_key"
    }

    @Provides
    fun provideKeyInterceptor(): Interceptor = Interceptor { chain ->
        val original = chain.request()
        original.url.newBuilder()
            .addQueryParameter(QUERY_PARAM, API_KEY)
            .build()
            .let {
                chain.proceed(
                    original.newBuilder().url(it).build()
                )
            }
    }

    @Provides
    fun provideOkhttp(
        interceptor: Interceptor
    ): OkHttpClient = OkHttpClient
        .Builder()
        .cache(null)
        .addInterceptor(interceptor)
        .build()

    @Provides
    fun provideJsonConverterFactory(): Converter.Factory {
        val contentType = "application/json".toMediaType()
        return Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }.asConverterFactory(contentType)
    }

    @Provides
    @Named("retrofit")
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        jsonConverter: Converter.Factory,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .client(okHttpClient)
        .addConverterFactory(jsonConverter)
        .build()

}
