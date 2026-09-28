package com.darkhorse.android.feature.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class SplashUiState {
    data object Loading : SplashUiState()
    data object NavigateToOnboarding : SplashUiState()
    data object NavigateToHome : SplashUiState()
}

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val appInitializer: AppInitializer,
) : ViewModel() {

    private val _uiState = MutableStateFlow<SplashUiState>(SplashUiState.Loading)
    val uiState: StateFlow<SplashUiState> = _uiState.asStateFlow()

    init {
        initializeApp()
    }

    private fun initializeApp() {
        viewModelScope.launch {
            appInitializer.initialize()

            if (appInitializer.isFirstInstall()) {
                _uiState.value = SplashUiState.NavigateToOnboarding
            } else {
                _uiState.value = SplashUiState.NavigateToHome
            }
        }
    }
}
