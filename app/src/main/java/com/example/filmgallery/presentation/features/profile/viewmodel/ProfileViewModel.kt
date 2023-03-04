package com.example.filmgallery.presentation.features.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmgallery.domain.model.Film
import com.example.filmgallery.domain.model.User
import com.example.filmgallery.domain.usecase.localfilm.GetLocalFilmsUseCase
import com.example.filmgallery.domain.usecase.user.GetUserUseCase
import com.example.filmgallery.domain.usecase.user.QuitUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val quitUseCase: QuitUseCase,
    private val getLocalFilmsUseCase: GetLocalFilmsUseCase
) : ViewModel() {

    private var _uiProfile: MutableStateFlow<User?>
        = MutableStateFlow(null)
    val uiProfile: StateFlow<User?> = _uiProfile

    private var _uiFilms: MutableStateFlow<List<Film>?>
            = MutableStateFlow(null)
    val uiFilms: StateFlow<List<Film>?> = _uiFilms

    private var _uiError: MutableStateFlow<Boolean>
            = MutableStateFlow(false)
    val uiError: StateFlow<Boolean> = _uiError

    init {
        getUser()
    }

    private fun getUser() {
        viewModelScope.launch {
            when(val result = getUserUseCase()) {
                null -> _uiError.value = true
                else -> _uiProfile.value = result
            }
        }
    }

    fun quit(isEntered: Boolean) {
        viewModelScope.launch {
            _uiProfile.value?.userId?.let {
                quitUseCase(it, isEntered)
                _uiProfile.value = null
                _uiError.value = true
            }
        }
    }

    fun getLocalFilms() {
        viewModelScope.launch {
            _uiProfile.value?.userName?.let {
                _uiFilms.value = getLocalFilmsUseCase(it)
            }
        }
    }
}
