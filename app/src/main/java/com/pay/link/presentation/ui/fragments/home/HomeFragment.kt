package com.pay.link.presentation.ui.fragments.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.viewModelScope
import com.pay.link.databinding.FragmentHomeBinding
import com.pay.link.presentation.ui.fragments.home.HomeViewEffect.Loading
import com.pay.link.presentation.utils.base.BaseFragment
import com.pay.link.presentation.utils.CustomProgressDialog
import com.pay.link.presentation.utils.SnackBarManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    FragmentHomeBinding::inflate,
    HomeViewModel::class
) {

    @Inject lateinit var progressDialog: CustomProgressDialog
    @Inject lateinit var snackBarManager: SnackBarManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        observeViewState()
        observeViewEffect()
    }


    private fun setListeners(){

    }

    private fun observeViewState(){
        viewModel.viewModelScope.launch {
            viewModel.viewState.collect{

            }
        }
    }

    private fun observeViewEffect(){
        viewModel.viewModelScope.launch {
            viewModel.viewEffect.collect {viewEffect ->
                when(viewEffect){
                    is Loading -> progressDialog.show(viewEffect.isLoading)
                }
            }
        }
    }

}