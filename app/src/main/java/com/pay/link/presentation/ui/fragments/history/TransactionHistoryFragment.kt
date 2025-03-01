package com.pay.link.presentation.ui.fragments.history

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.pay.link.databinding.FragmentTransactionHistoryBinding
import com.pay.link.presentation.adapters.TransactionHistoryAdapter
import com.pay.link.presentation.ui.fragments.history.TransactionHistoryViewEffect.NavigateBack
import com.pay.link.presentation.ui.fragments.history.TransactionHistoryViewEvent.OnBackClicked
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
    lateinit var progressDialog: CustomProgressDialog

    @Inject
    lateinit var snackBarManager: SnackBarManager

    private lateinit var adapter: TransactionHistoryAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        setupAdapter()
        observeViewState()
        observeViewEffect()
    }


    private fun setListeners() {

        progressDialog.attachToLifecycle(this, requireActivity())

        binding.backImageView.setOnClickListener { viewModel.onEvent(OnBackClicked) }

    }

    private fun observeViewState() {

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.viewState.collect { viewState ->

                adapter.updateData(viewState.transactions)
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

    private fun setupAdapter(){
        adapter = TransactionHistoryAdapter()
        binding.transactionHistoryRecyclerView.adapter = adapter
    }
}