package com.example.userprofiledemo.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.userprofiledemo.data.models.UserProfile
import com.example.userprofiledemo.network.NetworkClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface UserUiState {
    object Loading : UserUiState
    data class Success(val user: UserProfile) : UserUiState
    data class Failure(val message: String) : UserUiState
}

class UserViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<UserUiState>(UserUiState.Loading)

    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()

    init {
        fetchUser()
    }

    fun fetchUser() {
        viewModelScope.launch {
            _uiState.value = UserUiState.Loading
            try {
                val user = NetworkClient.apiService.getUserProfile(1)
                _uiState.value = UserUiState.Success(user)
            } catch (e: Exception) {
                _uiState.value = UserUiState.Failure(e.localizedMessage ?: "Unknown failure occurred")
            }
        }
    }
}