package com.example.filmgallery.data.repository

import android.util.Log
import com.example.filmgallery.data.network.api.Api
import com.example.filmgallery.domain.model.Actor
import com.example.filmgallery.domain.model.Serial
import com.example.filmgallery.domain.repository.SerialsRepository
import com.example.filmgallery.utils.ApplicationException
import com.example.filmgallery.utils.RequestResult
import javax.inject.Inject

class SerialsRepositoryImpl @Inject constructor(
    private val api: Api
) : SerialsRepository {

    override suspend fun getSerials(
        language: String,
        sortBy: String,
        page: Int,
        timezone: String,
        includeNullFirstAirDates: Boolean,
        monetization: String,
        withStatus: Int,
        withType: Int
    ): RequestResult<List<Serial>> = try {
        with(
            api.getSerials(
                language,
                sortBy,
                page,
                timezone,
                includeNullFirstAirDates,
                monetization,
                withStatus,
                withType
            )
        ) {
            if (isSuccessful && body() != null) {
                RequestResult.Success(
                    data = body()?.results?.map { it.toSerial() }
                )
            } else {
                RequestResult.Error(
                    message = ApplicationException.ApiException().message,
                    data = emptyList()
                )
            }
        }
    } catch (ex: Throwable) {
        RequestResult.Error(
            message = if (ex.message == "Connection reset") {
                ApplicationException.VPNException().message
            } else ApplicationException.InternetException().message
        )
    }

    override suspend fun getSerialsBySearch(
        language: String,
        query: String,
        includeAdult: Boolean
    ): RequestResult<List<Serial>> = try {
        val result = api.getSerialsBySearch(
            language,
            query,
            includeAdult
        )
        with(
            result
        ) {
            if (isSuccessful && body() != null) {
                RequestResult.Success(
                    data = body()?.results?.map { it.toSerial() }
                )
            } else {
                Log.e("FUCK", result.message())
                RequestResult.Error(
                    message = ApplicationException.ApiException().message,
                    data = emptyList()
                )
            }
        }
    } catch (ex: Throwable) {
        Log.e("FUCK", ex.toString())
        RequestResult.Error(
            message = if (ex.message == "Connection reset") {
                ApplicationException.VPNException().message
            } else ApplicationException.InternetException().message
        )
    }

    override suspend fun getSerialById(id: Int, language: String): RequestResult<Serial> = try {
        with(api.getSerialById(id, language)) {
            if (isSuccessful && body() != null) {
                RequestResult.Success(
                    data = body()?.toSerial()
                )
            } else RequestResult.Error(
                message = ApplicationException.ApiException().message,
            )
        }
    } catch (ex: Throwable) {
        RequestResult.Error(
            message = if (ex.message == "Connection reset") {
                ApplicationException.VPNException().message
            } else ApplicationException.InternetException().message
        )
    }

    override suspend fun getSerialCreditsById(
        id: Int,
        language: String
    ): RequestResult<List<Actor>> = try {
        with(api.getSerialCreditsById(id, language)) {
            if (isSuccessful && body() != null) {
                RequestResult.Success(
                    data = body()?.cast?.map { it.toActor() }
                )
            } else RequestResult.Error(
                message = ApplicationException.ApiException().message,
            )
        }
    } catch (ex: Throwable) {
        RequestResult.Error(
            message = if (ex.message == "Connection reset") {
                ApplicationException.VPNException().message
            } else ApplicationException.InternetException().message
        )
    }
}
