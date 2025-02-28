package com.pay.link.presentation.utils.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<VS : Any, VE : Any>(
    initialState: VS
) : ViewModel() {

    private val _viewState = MutableStateFlow(initialState)
    val viewState: StateFlow<VS> get() = _viewState

    private val _viewEffect = MutableSharedFlow<VE>()
    val viewEffect: SharedFlow<VE> get() = _viewEffect.asSharedFlow()

    protected fun setState(reducer: VS.() -> VS) {
        _viewState.value = _viewState.value.reducer()
    }

    protected fun sendEffect(effect: VE) {
        Log.e(TAG, "sendEffect: $effect")
        viewModelScope.launch {
            _viewEffect.emit(effect)
        }
    }

    companion object {
        private const val TAG = "BaseViewModel"
    }
}


