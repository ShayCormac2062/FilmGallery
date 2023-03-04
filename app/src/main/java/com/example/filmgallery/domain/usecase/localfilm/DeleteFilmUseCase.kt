package com.example.filmgallery.domain.usecase.localfilm

import com.example.filmgallery.domain.repository.LocalFilmsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class DeleteFilmUseCase @Inject constructor(
    private val repository: LocalFilmsRepository,
    @Named("io") private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(id: Int, userName: String) =
        withContext(dispatcher) {
            repository.deleteFilm(id, userName)
        }

}
