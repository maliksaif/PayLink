package com.pay.link.presentation.ui.fragments.history

import com.pay.link.domain.models.Transaction
import com.pay.link.domain.models.TransactionWithAccountNames

data class TransactionHistoryViewState(
    val transactions: List<TransactionWithAccountNames> = emptyList()
)


sealed class TransactionHistoryViewEffect {

    data object NavigateBack : TransactionHistoryViewEffect()

}


sealed class TransactionHistoryViewEvent {

    data object OnBackClicked : TransactionHistoryViewEvent()

}