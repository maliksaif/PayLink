package com.pay.link.presentation.ui.fragments.auth.login

import androidx.lifecycle.viewModelScope
import com.pay.link.domain.usecases.auth.SignInUseCase
import com.pay.link.domain.usecases.validations.ValidateEmailAddressUseCase
import com.pay.link.domain.usecases.validations.ValidatePasswordUseCase
import com.pay.link.presentation.ui.fragments.auth.login.LoginViewEvent.OnEmailChanged
import com.pay.link.presentation.ui.fragments.auth.login.LoginViewEvent.OnLoginClicked
import com.pay.link.presentation.ui.fragments.auth.login.LoginViewEvent.OnPasswordChanged
import com.pay.link.presentation.utils.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val validateEmailAddressUseCase: ValidateEmailAddressUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val signInUseCase: SignInUseCase
) :
    BaseViewModel<LoginViewState, LoginViewEffect>(
        LoginViewState()
    ) {


    private fun doLogin() {

        sendEffect(LoginViewEffect.Loading(isLoading = true))

        viewModelScope.launch {

            try {

                val results = signInUseCase(viewState.value.email, viewState.value.password)

                if (results.isSuccess)
                    sendEffect(LoginViewEffect.NavigateToHome)
                else
                    sendEffect(
                        LoginViewEffect.ShowErrorSnackBar(
                            results.exceptionOrNull()?.message ?: "Something went wrong"
                        )
                    )

            } catch (e: Exception) {
                sendEffect(LoginViewEffect.ShowErrorSnackBar(e.message ?: "Something went wrong"))
            } finally {
                sendEffect(LoginViewEffect.Loading(isLoading = false))
            }

        }


    }


    fun onEvent(event: LoginViewEvent) {

        when (event) {
            is OnEmailChanged -> {
                setState { copy(email = event.email) }
                validateFields()
            }

            is OnPasswordChanged -> {
                setState { copy(password = event.password) }
                validateFields()
            }

            OnLoginClicked -> doLogin()
        }
    }


    private fun validateFields() = with(viewState.value) {
        val isEmailValid = validateEmailAddressUseCase(email)
        val isPasswordValid = validatePasswordUseCase(password)

        setState { copy(isButtonEnabled = (isEmailValid && isPasswordValid)) }
    }

}