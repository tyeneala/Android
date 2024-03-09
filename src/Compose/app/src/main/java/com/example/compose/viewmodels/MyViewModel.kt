package com.example.compose.viewmodels

import androidx.lifecycle.ViewModel
import com.example.compose.modules.Password
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class UiState(
    val password: String = ""
)

class MyViewModel: ViewModel() {
    private val _state: MutableStateFlow<UiState> =
        MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()
    fun doPassword(domainName: String, keyword: String) {
        _state.update { it.copy(password = Password.get(domainName,keyword)) }
    }
}