package com.example.filmgallery.presentation.features.details.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmgallery.domain.model.Actor
import com.example.filmgallery.domain.model.Serial
import com.example.filmgallery.domain.usecase.film.GetSerialActorsUseCase
import com.example.filmgallery.domain.usecase.film.GetSerialByIdUseCase
import com.example.filmgallery.domain.usecase.user.GetUserNameUseCase
import com.example.filmgallery.utils.RequestResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.example.filmgallery.domain.usecase.localfilm.AddFilmUseCase as AddSerialUseCase
import com.example.filmgallery.domain.usecase.localfilm.DeleteFilmUseCase as DeleteSerialUseCase
import com.example.filmgallery.domain.usecase.localfilm.GetFilmByIdUseCase as LocalGetSerialByIdUseCase

@HiltViewModel
class SerialDetailsViewModel @Inject constructor(
    private val getSerialByIdUseCase: GetSerialByIdUseCase,
    private val addSerialUseCase: AddSerialUseCase,
    private val deleteSerialUseCase: DeleteSerialUseCase,
    private val localGetSerialByIdUseCase: LocalGetSerialByIdUseCase,
    private val getUserNameUseCase: GetUserNameUseCase,
    private val getSerialActorsUseCase: GetSerialActorsUseCase
) : ViewModel() {

    private var _uiState: MutableStateFlow<Serial?> =
        MutableStateFlow(null)
    val uiState: StateFlow<Serial?> = _uiState

    private var _uiError: MutableStateFlow<Boolean?> =
        MutableStateFlow(null)
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

    fun getSerialById(
        id: Int,
        language: String = "en-US"
    ) {
        viewModelScope.launch {
            when (val result = getSerialByIdUseCase(id, language)) {
                is RequestResult.Success -> {
                    _uiState.value = result.data
                    getSerialActors(id)
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
            deleteSerialUseCase(id, _uiUserName.value.toString())
            _uiFavorite.value = false
        }
    }

    fun addSerial(serial: Serial) {
        viewModelScope.launch {
            addSerialUseCase(serial.toFilm(), _uiUserName.value.toString())
            _uiFavorite.value = true
        }
    }

    fun getLocalSerialById(
        id: Int
    ) {
        _uiState.value = null
        _uiError.value = null
        viewModelScope.launch {
            when (val result = localGetSerialByIdUseCase(id, _uiUserName.value ?: "")) {
                null -> {
                    _uiFavorite.value = false
                    getSerialById(id)
                }
                else -> {
                    _uiFavorite.value = true
                    _uiState.value = result.toSerial()
                    getSerialActors(id)
                }
            }
        }
    }

    private fun getSerialActors(
        id: Int,
        language: String = "en-US"
    ) {
        viewModelScope.launch {
            _uiActors.value = when(
                val result = getSerialActorsUseCase(id, language)
            ) {
                is RequestResult.Success -> result.data
                else -> emptyList()
            }
        }
    }

}
