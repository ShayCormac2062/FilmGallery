package com.example.filmgallery.presentation.features.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmgallery.domain.model.User
import com.example.filmgallery.domain.usecase.user.AddUserUseCase
import com.example.filmgallery.domain.usecase.user.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val addUserUseCase: AddUserUseCase,
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {

    private var _uiRegister: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val uiRegister: StateFlow<Boolean?> = _uiRegister

    fun register(user: User) {
        viewModelScope.launch {
            addUserUseCase(user)
            getUser()
        }
    }

    private fun getUser() {
        viewModelScope.launch {
            _uiRegister.value = when (getUserUseCase()) {
                null -> false
                else -> true
            }
        }
    }

}
