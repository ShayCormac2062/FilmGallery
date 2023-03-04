package com.example.filmgallery.domain.usecase.film

import com.example.filmgallery.domain.repository.SerialsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class GetSerialBySearchUseCase @Inject constructor(
    private val repository: SerialsRepository,
    @Named("io") private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(
        language: String,
        query: String,
        includeAdult: Boolean
    ) = withContext(dispatcher) {
        repository.getSerialsBySearch(
            language,
            query,
            includeAdult
        )
    }
}
