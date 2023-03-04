package com.example.filmgallery.presentation.features.details.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmgallery.domain.model.Actor
import com.example.filmgallery.domain.model.Film
import com.example.filmgallery.domain.usecase.film.GetFilmActorsUseCase
import com.example.filmgallery.domain.usecase.film.GetFilmByIdUseCase
import com.example.filmgallery.domain.usecase.localfilm.AddFilmUseCase
import com.example.filmgallery.domain.usecase.localfilm.DeleteFilmUseCase
import com.example.filmgallery.domain.usecase.user.GetUserNameUseCase
import com.example.filmgallery.utils.RequestResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.example.filmgallery.domain.usecase.localfilm.GetFilmByIdUseCase as LocalGetFilmByIdUseCase

@HiltViewModel
class FilmDetailsViewModel @Inject constructor(
    private val getFilmByIdUseCase: GetFilmByIdUseCase,
    private val addFilmUseCase: AddFilmUseCase,
    private val deleteFilmUseCase: DeleteFilmUseCase,
    private val getUserNameUseCase: GetUserNameUseCase,
    private val localGetFilmByIdUseCase: LocalGetFilmByIdUseCase,
    private val getFilmActorsUseCase: GetFilmActorsUseCase
) : ViewModel() {

    private var _uiState: MutableStateFlow<Film?> =
        MutableStateFlow(null)
    val uiState: StateFlow<Film?> = _uiState

    private var _uiError: MutableStateFlow<Boolean?> =
        MutableStateFlow(false)
    val uiError: StateFlow<Boolean?> = _uiError

    private var _uiFavorite: MutableStateFlow<Boolean?> =
        MutableStateFlow(null)
    val uiFavorite: StateFlow<Boolean?> = _uiFavorite

    private var _uiUserName: MutableStateFlow<String?> =
        MutableStateFlow(null)
    val uiUserName: StateFlow<String?> = _uiUserName

    private var _uiActors: MutableStateFlow<List<Actor>?> =
        MutableStateFlow(null)
    val uiActors: StateFlow<List<Actor>?> = _uiActors

    init {
        viewModelScope.launch {
            getUserName()
        }
    }

    fun getFilmById(
        id: Int,
        language: String = "en-US"
    ) {
        _uiState.value = null
        _uiError.value = false
        viewModelScope.launch {
            when (val result = getFilmByIdUseCase(id, language)) {
                is RequestResult.Success -> {
                    _uiState.value = result.data
                    getFilmActors(id)
                }
                is RequestResult.Error -> {
                    _uiError.value = true
                }
            }
        }
    }

    private suspend fun getUserName() {
        val userName = withContext(viewModelScope.coroutineContext) {
            getUserNameUseCase().toString()
        }
        _uiUserName.value = userName
    }

    fun deleteFilm(id: Int) {
        viewModelScope.launch {
            deleteFilmUseCase(id, _uiUserName.value.toString())
            _uiFavorite.value = false
        }
    }

    fun addFilm(film: Film) {
        viewModelScope.launch {
            addFilmUseCase(film, _uiUserName.value.toString())
            _uiFavorite.value = true
        }
    }

    fun getLocalFilmById(
        id: Int
    ) {
        _uiState.value = null
        _uiError.value = null
        viewModelScope.launch {
            when (val result = localGetFilmByIdUseCase(id, _uiUserName.value ?: "")) {
                null -> {
                    _uiFavorite.value = false
                    getFilmById(id)
                }
                else -> {
                    _uiFavorite.value = true
                    _uiState.value = result
                    getFilmActors(id)
                }
            }
        }
    }

    private fun getFilmActors(
        id: Int,
        language: String = "en-US"
    ) {
        viewModelScope.launch {
            _uiActors.value = when(
                val result = getFilmActorsUseCase(id, language)
            ) {
                is RequestResult.Success -> result.data
                else -> emptyList()
            }
        }
    }
}
