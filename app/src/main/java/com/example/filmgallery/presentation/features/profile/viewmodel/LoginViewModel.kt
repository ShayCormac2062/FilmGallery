package com.example.filmgallery.presentation.features.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmgallery.domain.usecase.user.LoginUserUseCase
import com.example.filmgallery.domain.usecase.user.QuitUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase,
    private val quitUseCase: QuitUseCase
) : ViewModel() {

    private var _uiLogin: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val uiLogin: StateFlow<Boolean?> = _uiLogin

    fun login(userName: String, password: String) {
        viewModelScope.launch {
            _uiLogin.value = when (val user = loginUserUseCase(userName, password)) {
                null -> false
                else -> {
                    quitUseCase(user.userId, true)
                    true
                }
            }
        }
    }

}
