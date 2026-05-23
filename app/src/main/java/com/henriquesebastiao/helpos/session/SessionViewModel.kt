package com.henriquesebastiao.helpos.session

import androidx.lifecycle.ViewModel
import com.henriquesebastiao.helpos.domain.model.SessionState
import com.henriquesebastiao.helpos.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(
    authRepository: AuthRepository,
) : ViewModel() {
    val sessionState: StateFlow<SessionState> = authRepository.sessionState
}
