package com.example.filmgallery.domain.usecase.film

import com.example.filmgallery.domain.repository.SerialsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class GetSerialsUseCase @Inject constructor(
    private val repository: SerialsRepository,
    @Named("io") private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(
        language: String,
        sortBy: String,
        page: Int,
        timezone: String,
        includeNullFirstAirDates: Boolean,
        monetization: String,
        withStatus: Int,
        withType: Int,
    ) = withContext(dispatcher) {
        repository.getSerials(
            language,
            sortBy, page,
            timezone,
            includeNullFirstAirDates,
            monetization,
            withStatus,
            withType
        )
    }

}
