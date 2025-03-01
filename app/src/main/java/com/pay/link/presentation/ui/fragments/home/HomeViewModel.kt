package com.pay.link.presentation.ui.fragments.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.pay.link.domain.usecases.accounts.DeleteAllAccountsUseCase
import com.pay.link.domain.usecases.accounts.GenerateMockAccountDataUseCase
import com.pay.link.domain.usecases.accounts.GetAccountsUseCase
import com.pay.link.domain.usecases.auth.SignOutUseCase
import com.pay.link.domain.usecases.common.GenerateAccountNumberUseCase
import com.pay.link.domain.usecases.common.GenerateRandomNameUseCase
import com.pay.link.presentation.ui.fragments.home.HomeViewEffect.NavigateToSignIn
import com.pay.link.presentation.ui.fragments.home.HomeViewEffect.NavigateToTransactionHistory
import com.pay.link.presentation.ui.fragments.home.HomeViewEffect.NavigateToTransfer
import com.pay.link.presentation.ui.fragments.home.HomeViewEffect.ShowErrorSnackbar
import com.pay.link.presentation.ui.fragments.home.HomeViewEvent.OnAccountClicked
import com.pay.link.presentation.ui.fragments.home.HomeViewEvent.OnRefresh
import com.pay.link.presentation.ui.fragments.home.HomeViewEvent.OnSignOutClicked
import com.pay.link.presentation.ui.fragments.home.HomeViewEvent.OnSignOutConfirmation
import com.pay.link.presentation.ui.fragments.home.HomeViewEvent.OnTransactionHistoryClicked
import com.pay.link.presentation.ui.fragments.home.HomeViewEvent.OnTransferClicked
import com.pay.link.presentation.utils.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAccountsUseCase: GetAccountsUseCase,
    private val generateMockAccountDataUseCase: GenerateMockAccountDataUseCase,
    private val generateAccountNumberUseCase: GenerateAccountNumberUseCase,
    private val generateRandomNameUseCase: GenerateRandomNameUseCase,
    private val deleteAllAccountsUseCase: DeleteAllAccountsUseCase,
    private val signOutUseCase: SignOutUseCase

) : BaseViewModel<HomeViewState, HomeViewEffect>(
    HomeViewState()
) {

    companion object {
        private const val TAG = "HomeViewModel"
    }


    private val mutex = Mutex()


    init {

        checkAndGenerateMockData()
    }

    private fun checkAndGenerateMockData() {

        viewModelScope.launch {
            setState { copy( isLoading = true) }
            mutex.withLock {
                withContext(Dispatchers.IO) {
                    val existingAccounts = getAccountsUseCase()
                    if (existingAccounts.isEmpty()) {
                        generateMockData()
                    }
                }

                delay(1000)
                val updatedAccounts = withContext(Dispatchers.IO) { getAccountsUseCase() }
                setState { copy(accounts = updatedAccounts, isLoading = false) }
            }
        }
    }

    /*
    *
    * Generating Mock Data
    *
    * */
    private suspend fun generateMockData() {
        runCatching {
            repeat(10) {
                val mockName = generateRandomNameUseCase()
                val mockNumber = generateAccountNumberUseCase()
                val mockBalance = (1..5000).random().toDouble()

                generateMockAccountDataUseCase(
                    name = mockName,
                    number = mockNumber,
                    balance = mockBalance
                )
            }
        }.onFailure { error ->
            withContext(Dispatchers.Main) {
                sendEffect(ShowErrorSnackbar(error.message ?: "Failed to generate mock data"))
            }
        }
    }

    fun onEvent(event: HomeViewEvent) {
        when (event) {
            is OnAccountClicked -> {
                // TODO if needed for account details
            }
            OnTransactionHistoryClicked -> sendEffect(NavigateToTransactionHistory)
            OnTransferClicked -> sendEffect(NavigateToTransfer)
            OnRefresh ->  checkAndGenerateMockData()
            OnSignOutClicked -> {
                 val response = signOutUseCase()

                if(response.isSuccess){
                    sendEffect(NavigateToSignIn)
                }else {
                    sendEffect(ShowErrorSnackbar(response.exceptionOrNull()?.message ?: "Failed to sign out"))
                }
            }
            OnSignOutConfirmation -> {

            }
        }
    }
}
