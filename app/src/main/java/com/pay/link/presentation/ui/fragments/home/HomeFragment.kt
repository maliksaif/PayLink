package com.pay.link.presentation.ui.fragments.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.pay.link.R
import com.pay.link.databinding.FragmentHomeBinding
import com.pay.link.presentation.adapters.AccountsAdapter
import com.pay.link.presentation.ui.activities.main.MainActivity
import com.pay.link.presentation.ui.fragments.home.HomeFragmentDirections.Companion.toTransactionHistory
import com.pay.link.presentation.ui.fragments.home.HomeFragmentDirections.Companion.toTransfer
import com.pay.link.presentation.ui.fragments.home.HomeViewEffect.NavigateToSignIn
import com.pay.link.presentation.ui.fragments.home.HomeViewEffect.NavigateToTransactionHistory
import com.pay.link.presentation.ui.fragments.home.HomeViewEffect.NavigateToTransfer
import com.pay.link.presentation.ui.fragments.home.HomeViewEffect.ShowErrorSnackbar
import com.pay.link.presentation.ui.fragments.home.HomeViewEffect.ShowSignOutDialog
import com.pay.link.presentation.ui.fragments.home.HomeViewEffect.ShowSuccessSnackbar
import com.pay.link.presentation.ui.fragments.home.HomeViewEvent.OnAccountClicked
import com.pay.link.presentation.ui.fragments.home.HomeViewEvent.OnRefresh
import com.pay.link.presentation.ui.fragments.home.HomeViewEvent.OnSignOutClicked
import com.pay.link.presentation.ui.fragments.home.HomeViewEvent.OnTransactionHistoryClicked
import com.pay.link.presentation.ui.fragments.home.HomeViewEvent.OnTransferClicked
import com.pay.link.presentation.utils.CustomProgressDialog
import com.pay.link.presentation.utils.SnackBarManager
import com.pay.link.presentation.utils.base.BaseFragment
import com.pay.link.presentation.utils.sharedviewmodel.TransferSharedViewModel
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

    private val transferSharedViewModel: TransferSharedViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        setAccountsRecyclerView()
        observeViewState()
        observeViewEffect()
    }


    private fun setListeners() {
        progressDialog.attachToLifecycle(this, requireActivity())

        binding.transferActionButton.setOnClickListener { viewModel.onEvent(OnTransferClicked) }
        binding.transactionHistoryActionButton.setOnClickListener {
            viewModel.onEvent(
                OnTransactionHistoryClicked
            )
        }

        binding.imageViewSignOut.setOnClickListener { viewModel.onEvent(OnSignOutClicked) }

        transferSharedViewModel.refreshTrigger.observe(viewLifecycleOwner) { shouldRefresh ->
            if (shouldRefresh) {
                viewModel.onEvent(OnRefresh)
            }
        }

    }

    private fun observeViewState() {
        viewModel.viewModelScope.launch {
            viewModel.viewState.collectLatest { viewState ->
                progressDialog.show(viewState.isLoading)

                accountsAdapter.updateData(viewState.accounts)
            }
        }
    }

    private fun observeViewEffect() {
        viewModel.viewModelScope.launch {
            viewModel.viewEffect.collect { viewEffect ->
                when (viewEffect) {
                    is ShowErrorSnackbar -> snackBarManager.showErrorSnackBar(
                        binding.root,
                        viewEffect.message
                    )

                    is ShowSuccessSnackbar -> snackBarManager.showSuccessSnackBar(
                        binding.root,
                        viewEffect.message
                    )

                    NavigateToTransactionHistory -> navigateToTransactionHistory()
                    NavigateToTransfer -> navigateToTransfer()

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

    /*
    *
    * Due to Fragment destroy / recreation
    *
    * */

    private fun navigateToTransfer() {
        val navController = findNavController()
        if (navController.currentDestination?.id == R.id.home_fragment) {
            navController.navigate(toTransfer())
        } else {
            Log.e(TAG, "NavController is in an invalid state")
        }
    }

    private fun navigateToTransactionHistory() {
        val navController = findNavController()
        if (navController.currentDestination?.id == R.id.home_fragment) {
            navController.navigate(toTransactionHistory())
        } else {
            Log.e(TAG, "NavController is in an invalid state")
        }
    }

    companion object {
        private const val TAG = "HomeFragment"
    }
}