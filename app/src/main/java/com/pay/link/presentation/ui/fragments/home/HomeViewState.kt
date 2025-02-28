package com.pay.link.presentation.ui.fragments.home

data class HomeViewState(val username : String = "")


sealed class HomeViewEffect {

    data class Loading(val isLoading : Boolean) : HomeViewEffect()

}

sealed class HomeViewEvent {


}