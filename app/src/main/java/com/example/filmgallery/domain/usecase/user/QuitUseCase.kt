package com.example.filmgallery.domain.usecase.user

import com.example.filmgallery.domain.repository.UsersRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class QuitUseCase @Inject constructor(
    private val repository: UsersRepository,
    @Named("io") private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(id: Int, isEntered: Boolean) =
        withContext(dispatcher) {
            repository.quit(id, isEntered)
        }

}
