package com.pay.link.presentation.ui.fragments.history

import androidx.lifecycle.viewModelScope
import com.pay.link.domain.usecases.transfer.GetTransactionsWithNamesUseCase
import com.pay.link.presentation.ui.fragments.history.TransactionHistoryViewEffect.NavigateBack
import com.pay.link.presentation.ui.fragments.history.TransactionHistoryViewEvent.OnBackClicked
import com.pay.link.presentation.utils.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TransactionHistoryViewModel @Inject constructor(
    private val getTransactionHistoryUseCase: GetTransactionsWithNamesUseCase
) :
    BaseViewModel<TransactionHistoryViewState, TransactionHistoryViewEffect>(
        TransactionHistoryViewState()
    ) {


    init {

        viewModelScope.launch {
            val transactions = withContext(Dispatchers.IO) {
                getTransactionHistoryUseCase()
            }

            setState {
                copy(transactions = transactions)
            }
        }

    }


    fun onEvent(event : TransactionHistoryViewEvent){
        when(event){
            OnBackClicked -> sendEffect(NavigateBack)
        }
    }

}