package com.example.filmgallery.presentation.features.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmgallery.domain.model.BaseElement
import com.example.filmgallery.domain.usecase.film.GetFilmsUseCase
import com.example.filmgallery.domain.usecase.film.GetSerialsUseCase
import com.example.filmgallery.utils.RequestResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getFilmsUseCase: GetFilmsUseCase,
    private val getSerialsUseCase: GetSerialsUseCase
) : ViewModel() {

    private var _uiState: MutableStateFlow<ArrayList<BaseElement>?> =
        MutableStateFlow(null)
    val uiState: StateFlow<ArrayList<BaseElement>?> = _uiState

    private var _uiError: MutableStateFlow<String?> =
        MutableStateFlow(null)
    val uiError: StateFlow<String?> = _uiError

    init {
        getFilms(isNeedToUpdate = true)
        getSerials()
    }

    fun getFilms(
        language: String = "en-US",
        sortBy: String = "popularity.desc",
        includeAdult: Boolean = false,
        includeVideo: Boolean = false,
        page: Int = 1,
        monetization: String = "flatrate",
        isNeedToUpdate: Boolean
    ) {
        if (isNeedToUpdate) _uiState.value = null
        _uiError.value = null
        viewModelScope.launch {
            when (
                val result = getFilmsUseCase(
                    language,
                    sortBy,
                    includeAdult,
                    includeVideo,
                    page,
                    monetization
                )
            ) {
                is RequestResult.Success -> {
                    updateList(result.data)
                }
                is RequestResult.Error -> {
                    _uiError.value = result.message
                }
            }
        }
    }

    fun getSerials(
        language: String = "en-US",
        sortBy: String = "popularity.desc",
        page: Int = 1,
        timezone: String = "America%2FNew_York",
        includeNullFirstAirDates: Boolean = false,
        monetization: String = "flatrate",
        withStatus: Int = 0,
        withType: Int = 0,
    ) {
        viewModelScope.launch {
            when (
                val result = getSerialsUseCase(
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
                is RequestResult.Success -> {
                    updateList(result.data)
                }
                is RequestResult.Error -> {
                    _uiError.value = result.message
                }
            }
        }
    }

    private fun updateList(data: List<BaseElement>?) {
        val newList = arrayListOf<BaseElement>()
        data?.forEach {
            newList.add(it)
        }
        if (_uiState.value?.isNotEmpty() == true) {
            newList.forEach {
                _uiState.value?.add(it)
            }
        } else {
            _uiState.value = newList
        }
    }

}
