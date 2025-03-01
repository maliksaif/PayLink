package com.pay.link.presentation.utils.sharedviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TransferSharedViewModel : ViewModel() {
    private val _refreshTrigger = MutableLiveData<Boolean>()
    val refreshTrigger: LiveData<Boolean> get() = _refreshTrigger

    fun notifyTransferSuccess() {
        _refreshTrigger.value = true
    }
}
