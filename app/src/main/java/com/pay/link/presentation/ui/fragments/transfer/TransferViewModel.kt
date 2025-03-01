package com.pay.link.presentation.ui.fragments.transfer

import androidx.lifecycle.viewModelScope
import com.pay.link.domain.usecases.accounts.GetAccountsUseCase
import com.pay.link.domain.usecases.transfer.TransferFundsUseCase
import com.pay.link.presentation.ui.fragments.transfer.TransferViewEffect.NavigateBack
import com.pay.link.presentation.ui.fragments.transfer.TransferViewEffect.SetSourceDestinationAdapters
import com.pay.link.presentation.ui.fragments.transfer.TransferViewEvent.OnAmountToTransferChanged
import com.pay.link.presentation.ui.fragments.transfer.TransferViewEvent.OnBackClicked
import com.pay.link.presentation.ui.fragments.transfer.TransferViewEvent.OnDestinationAccountSelected
import com.pay.link.presentation.ui.fragments.transfer.TransferViewEvent.OnProceedClicked
import com.pay.link.presentation.ui.fragments.transfer.TransferViewEvent.OnSourceAccountSelected
import com.pay.link.presentation.ui.fragments.transfer.TransferViewEvent.OnTransferConfirmationClicked
import com.pay.link.presentation.utils.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TransferViewModel @Inject constructor(
    private val getAccountsUseCase: GetAccountsUseCase,
    private val transferFundsUseCase: TransferFundsUseCase
) : BaseViewModel<TransferViewState, TransferViewEffect>(TransferViewState()) {



    init {

        viewModelScope.launch {
            val accounts = withContext(Dispatchers.IO) {
                getAccountsUseCase()
            }
            setState { copy(accounts = accounts) }
            sendEffect(SetSourceDestinationAdapters(accounts))
        }


    }


    fun onEvent(event: TransferViewEvent) {
        when (event) {
            OnBackClicked -> sendEffect(NavigateBack)
            is OnAmountToTransferChanged -> {
                val enteredAmount = event.amount.toDoubleOrNull() ?: 0.0
                val availableBalance = viewState.value.sourceAccount?.balance ?: 0.0

                val showError = enteredAmount > availableBalance

                setState {
                    copy(
                        amountToTransfer = enteredAmount,
                        amountError = if (showError) "Amount exceeds available balance" else null,
                        isTransferButtonEnabled = isTransferEnabled(enteredAmount)
                    )
                }
            }


            is OnDestinationAccountSelected -> setState {
                copy(
                    destinationAccount = event.account,
                    isTransferButtonEnabled = isTransferEnabled()
                )
            }

            is OnSourceAccountSelected -> setState {
                copy(
                    sourceAccount = event.account,
                    isTransferButtonEnabled = isTransferEnabled()
                )
            }

            OnProceedClicked -> sendEffect(
                TransferViewEffect.ShowTransferConfirmationBottomSheet(
                    viewState.value.sourceAccount!!,
                    viewState.value.destinationAccount!!,
                    viewState.value.amountToTransfer
                )
            )

            OnTransferConfirmationClicked -> {

                viewModelScope.launch {
                    withContext(Dispatchers.IO){
                        val transferResult = transferFundsUseCase(
                            sourceId = viewState.value.sourceAccount!!.id,
                            destinationId = viewState.value.destinationAccount!!.id,
                            amount = viewState.value.amountToTransfer
                        )

                    }
                    sendEffect(TransferViewEffect.OnTransferSuccess(message = "Transfer Success!!"))
                }
            }
        }
    }

    private fun isTransferEnabled(amount: Double = viewState.value.amountToTransfer): Boolean {
        val sourceAccount = viewState.value.sourceAccount
        val destinationAccount = viewState.value.destinationAccount
        val availableBalance = sourceAccount?.balance ?: 0.0

        return sourceAccount != null &&
                destinationAccount != null &&
                amount > 0.0 &&
                amount <= availableBalance
    }

}