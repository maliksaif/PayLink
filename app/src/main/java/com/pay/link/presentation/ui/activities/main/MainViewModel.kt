package com.pay.link.presentation.ui.activities.main

import androidx.lifecycle.ViewModel
import com.pay.link.domain.usecases.auth.GetCurrentUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {


    fun isUserLoggedIn() : Boolean {
        val currentUser = getCurrentUserUseCase()
        return currentUser != null
    }
}
