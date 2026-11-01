package com.goldencrust.app.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.goldencrust.app.domain.onboarding.edit.SetOnboardingCompletedUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OnboardingViewModel(
    private val setOnboardingCompleted: SetOnboardingCompletedUseCase,
) : ViewModel() {
    private val log = Logger.withTag("OnboardingVM")

    private val _state = MutableStateFlow(OnboardingState())
    val state: StateFlow<OnboardingState> = _state.asStateFlow()

    private val _events = Channel<OnboardingEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    fun onAction(action: OnboardingAction) {
        when (action) {
            is OnboardingAction.PageChanged -> _state.update { it.copy(currentPage = action.page) }
            OnboardingAction.Skip, OnboardingAction.Finish -> complete()
        }
    }

    private fun complete() {
        viewModelScope.launch {
            setOnboardingCompleted()
            log.i { "Onboarding completed" }
            _events.send(OnboardingEvent.NavigateToLogin)
        }
    }
}