package com.pay.link.presentation.ui.fragments.auth.login


data class LoginViewState(
    val email: String = "",
    val password: String = "",
    val isButtonEnabled: Boolean = false

)

sealed class LoginViewEffect {

    data class Loading(val isLoading: Boolean) : LoginViewEffect()


    data class ShowErrorSnackBar(val message: String) : LoginViewEffect()
    data class ShowSuccessSnackBar(val message: String) : LoginViewEffect()

    data object NavigateToHome : LoginViewEffect()

}

sealed class LoginViewEvent {


    data class OnEmailChanged(val email: String) : LoginViewEvent()
    data class OnPasswordChanged(val password: String) : LoginViewEvent()

    data object OnLoginClicked : LoginViewEvent()

}