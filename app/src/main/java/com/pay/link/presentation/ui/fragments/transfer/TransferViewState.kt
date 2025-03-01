package com.pay.link.presentation.ui.fragments.transfer

import com.pay.link.domain.models.Account

data class TransferViewState(
    var accounts: List<Account> = emptyList(),
    var sourceAccount: Account? = null,
    var destinationAccount: Account? = null,
    var amountToTransfer: Double = 0.0,
    var amountError : String? = null,
    var isTransferButtonEnabled : Boolean = false,
    val isLoading : Boolean = false
)


sealed class TransferViewEffect {

    data object NavigateBack : TransferViewEffect()
    data class SetSourceDestinationAdapters(val accounts: List<Account>) : TransferViewEffect()
    data class ShowErrorSnackBar(val message: String) : TransferViewEffect()
    data class ShowSuccessSnackBar(val message: String) : TransferViewEffect()
    data class OnTransferSuccess(val message: String) : TransferViewEffect()
    data class ShowTransferConfirmationBottomSheet(
        val sourceAccount: Account,
        val destinationAccount: Account,
        val amountToTransfer: Double
    ) : TransferViewEffect()



}

sealed class TransferViewEvent {

    data class OnSourceAccountSelected(val account: Account) : TransferViewEvent()
    data class OnDestinationAccountSelected(val account: Account) : TransferViewEvent()
    data class OnAmountToTransferChanged(val amount: String) : TransferViewEvent()
    data object OnProceedClicked : TransferViewEvent()
    data object OnTransferConfirmationClicked : TransferViewEvent()
    data object OnBackClicked : TransferViewEvent()

}