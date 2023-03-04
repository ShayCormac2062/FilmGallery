package com.example.filmgallery.domain.usecase.film

import com.example.filmgallery.domain.repository.FilmsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class GetFilmsUseCase @Inject constructor(
    private val repository: FilmsRepository,
    @Named("io") private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(
        language: String,
        sortBy: String,
        includeAdult: Boolean,
        includeVideo: Boolean,
        page: Int,
        monetization: String
    ) = withContext(dispatcher) {
        repository.getFilms(
            language,
            sortBy,
            includeAdult,
            includeVideo,
            page,
            monetization
        )
    }

}
