package com.pay.link.presentation.ui.fragments.home

import com.pay.link.domain.models.Account

data class HomeViewState(
    val isLoading: Boolean = false,
    val accounts: List<Account> = emptyList()
)


sealed class HomeViewEffect {

    data class ShowErrorSnackbar(val message : String) : HomeViewEffect()
    data class ShowSuccessSnackbar(val message: String) : HomeViewEffect()

    data object NavigateToTransfer : HomeViewEffect()
    data object NavigateToTransactionHistory : HomeViewEffect()

    data object ShowSignOutDialog : HomeViewEffect()

    data object NavigateToSignIn : HomeViewEffect()

}

sealed class HomeViewEvent {

    data class OnAccountClicked(val account: Account) : HomeViewEvent()
    data object OnTransferClicked : HomeViewEvent()
    data object OnTransactionHistoryClicked : HomeViewEvent()

    data object OnRefresh : HomeViewEvent()
    data object OnSignOutClicked : HomeViewEvent()
    data object OnSignOutConfirmation : HomeViewEvent()

}