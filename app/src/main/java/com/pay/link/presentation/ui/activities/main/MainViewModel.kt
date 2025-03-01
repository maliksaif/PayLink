package com.pay.link.presentation.ui.activities.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pay.link.domain.usecases.auth.GetCurrentUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    private val _isUserLoggedInLiveData = MutableLiveData<Boolean>()
    val isUserLoggedInLiveData: LiveData<Boolean> = _isUserLoggedInLiveData

    init {
        checkAuthState()
    }

    private fun checkAuthState() {
        _isUserLoggedInLiveData.value = getCurrentUserUseCase() != null
    }

    fun updateAuthState() {
        _isUserLoggedInLiveData.value = getCurrentUserUseCase() != null
    }
}

