package com.pay.link.presentation.ui.fragments.history

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.pay.link.databinding.FragmentTransactionHistoryBinding
import com.pay.link.presentation.ui.fragments.history.TransactionHistoryViewEffect.NavigateBack
import com.pay.link.presentation.utils.CustomProgressDialog
import com.pay.link.presentation.utils.SnackBarManager
import com.pay.link.presentation.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TransactionHistoryFragment : BaseFragment<FragmentTransactionHistoryBinding,TransactionHistoryViewModel>(
    FragmentTransactionHistoryBinding::inflate,
    TransactionHistoryViewModel::class
) {


    @Inject
    lateinit var progressBar: CustomProgressDialog

    @Inject
    lateinit var snackBarManager: SnackBarManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        observeViewState()
        observeViewEffect()
    }


    private fun setListeners() {

    }

    private fun observeViewState() {

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.viewState.collect { viewState ->

                Log.d("TAG", "observeViewState: "+viewState)
            }
        }

    }

    private fun observeViewEffect() {


        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.viewEffect.collect { viewEffect ->
                when (viewEffect) {
                    NavigateBack -> findNavController().navigateUp()
                }
            }
        }

    }
}