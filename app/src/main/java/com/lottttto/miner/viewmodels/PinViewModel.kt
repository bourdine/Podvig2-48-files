package com.lottttto.miner.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lottttto.miner.repositories.PinRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PinViewModel @Inject constructor(
    private val pinRepository: PinRepositoryImpl
) : ViewModel() {

    private val _uiState = MutableStateFlow(PinUiState())
    val uiState: StateFlow<PinUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val first = pinRepository.isFirstLaunch()
            val hasPin = pinRepository.hasPin()
            _uiState.update { it.copy(isFirstLaunch = first, isPinSet = hasPin) }
        }
    }

    fun setPin(pin: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val hash = PinRepositoryImpl.hashPin(pin)
            pinRepository.setPin(hash)
            _uiState.update { it.copy(isPinSet = true) }
            onSuccess()
        }
    }

    fun checkPin(pin: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val hash = PinRepositoryImpl.hashPin(pin)
            val isValid = pinRepository.checkPin(hash)
            onResult(isValid)
        }
    }

    fun markPinValidated() {
        _uiState.update { it.copy(isPinValidated = true) }
    }

    fun logout() {
        viewModelScope.launch {
            pinRepository.clearPin()
            _uiState.update { it.copy(isPinSet = false, isPinValidated = false) }
        }
    }
}

data class PinUiState(
    val isFirstLaunch: Boolean = true,
    val isPinSet: Boolean = false,
    val isPinValidated: Boolean = false
)
