package com.pay.link.presentation.ui.fragments.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.pay.link.databinding.FragmentHomeBinding
import com.pay.link.presentation.adapters.AccountsAdapter
import com.pay.link.presentation.ui.activities.main.MainActivity
import com.pay.link.presentation.ui.fragments.home.HomeViewEffect.Loading
import com.pay.link.presentation.ui.fragments.home.HomeViewEffect.NavigateToSignIn
import com.pay.link.presentation.ui.fragments.home.HomeViewEffect.ShowErrorSnackbar
import com.pay.link.presentation.ui.fragments.home.HomeViewEffect.ShowSignOutDialog
import com.pay.link.presentation.ui.fragments.home.HomeViewEffect.ShowSuccessSnackbar
import com.pay.link.presentation.ui.fragments.home.HomeViewEvent.OnAccountClicked
import com.pay.link.presentation.ui.fragments.home.HomeViewEvent.OnSignOutClicked
import com.pay.link.presentation.ui.fragments.home.HomeViewEvent.OnTransactionHistoryClicked
import com.pay.link.presentation.ui.fragments.home.HomeViewEvent.OnTransferClicked
import com.pay.link.presentation.utils.base.BaseFragment
import com.pay.link.presentation.utils.CustomProgressDialog
import com.pay.link.presentation.utils.SnackBarManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    FragmentHomeBinding::inflate,
    HomeViewModel::class
) {

    @Inject
    lateinit var progressDialog: CustomProgressDialog

    @Inject
    lateinit var snackBarManager: SnackBarManager

    private lateinit var accountsAdapter: AccountsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        setAccountsRecyclerView()
        observeViewState()
        observeViewEffect()
    }


    private fun setListeners() {

        binding.transferActionButton.setOnClickListener { viewModel.onEvent(OnTransferClicked) }
        binding.transactionHistoryActionButton.setOnClickListener {
            viewModel.onEvent(
                OnTransactionHistoryClicked
            )
        }

        binding.imageViewSignOut.setOnClickListener { viewModel.onEvent(OnSignOutClicked) }


    }

    private fun observeViewState() {
        viewModel.viewModelScope.launch {
            viewModel.viewState.collectLatest { viewState ->

                accountsAdapter.updateData(viewState.accounts)
            }
        }
    }

    private fun observeViewEffect() {
        viewModel.viewModelScope.launch {
            viewModel.viewEffect.collect { viewEffect ->
                when (viewEffect) {
                    is Loading -> progressDialog.show(viewEffect.isLoading)
                    is ShowErrorSnackbar -> snackBarManager.showErrorSnackBar(
                        binding.root,
                        viewEffect.message
                    )

                    is ShowSuccessSnackbar -> snackBarManager.showSuccessSnackBar(
                        binding.root,
                        viewEffect.message
                    )

                    HomeViewEffect.NavigateToTransactionHistory -> {
//                        findNavController().navigate()
                    }

                    HomeViewEffect.NavigateToTransfer -> {
                        //                        findNavController().navigate()

                    }

                    NavigateToSignIn -> {
                        val intent = Intent(requireContext(), MainActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()
                    }
                    ShowSignOutDialog -> TODO()
                }
            }
        }
    }

    private fun setAccountsRecyclerView() {

        accountsAdapter = AccountsAdapter(onItemClick = {
            viewModel.onEvent(OnAccountClicked(it))
        })
        binding.accountRecyclerView.adapter = accountsAdapter

    }

}