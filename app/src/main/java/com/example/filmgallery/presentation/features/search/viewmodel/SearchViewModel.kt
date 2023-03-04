package com.example.filmgallery.presentation.features.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmgallery.domain.model.BaseElement
import com.example.filmgallery.domain.usecase.film.GetFilmBySearchUseCase
import com.example.filmgallery.domain.usecase.film.GetSerialBySearchUseCase
import com.example.filmgallery.utils.RequestResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getFilmBySearchUseCase: GetFilmBySearchUseCase,
    private val getSerialBySearchUseCase: GetSerialBySearchUseCase
) : ViewModel() {

    private var _uiFilmState: MutableStateFlow<List<BaseElement>?> =
        MutableStateFlow(emptyList())
    val uiFilmState: StateFlow<List<BaseElement>?> = _uiFilmState

    private var _uiFilmError: MutableStateFlow<String?> =
        MutableStateFlow(null)
    val uiFilmError: StateFlow<String?> = _uiFilmError

    private var _uiSerialState: MutableStateFlow<List<BaseElement>?> =
        MutableStateFlow(emptyList())
    val uiSerialState: StateFlow<List<BaseElement>?> = _uiSerialState

    private var _uiSerialError: MutableStateFlow<String?> =
        MutableStateFlow(null)
    val uiSerialError: StateFlow<String?> = _uiSerialError

    fun getFilmsBySearch(
        language: String = "en-US",
        query: String,
        includeAdult: Boolean = false
    ) {
        _uiFilmState.value = null
        _uiFilmError.value = null
        viewModelScope.launch {
            when (
                val result = getFilmBySearchUseCase(
                    language,
                    query,
                    includeAdult
                )
            ) {
                is RequestResult.Success -> {
                    _uiFilmState.value = result.data
                }
                is RequestResult.Error -> {
                    _uiFilmError.value = result.message
                }
            }
        }
    }

    fun getSerialsBySearch(
        language: String = "en-US",
        query: String,
        includeAdult: Boolean = false
    ) {
        _uiSerialState.value = null
        _uiSerialError.value = null
        viewModelScope.launch {
            when (
                val result = getSerialBySearchUseCase(
                    language,
                    query,
                    includeAdult
                )
            ) {
                is RequestResult.Success -> {
                    _uiSerialState.value = result.data
                }
                is RequestResult.Error -> {
                    _uiSerialError.value = result.message
                }
            }
        }
    }

}
