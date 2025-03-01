package com.pay.link.presentation.ui.fragments.transfer

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.pay.link.R
import com.pay.link.databinding.FragmentTransferBinding
import com.pay.link.domain.models.Account
import com.pay.link.presentation.adapters.AccountAutoCompleteAdapter
import com.pay.link.presentation.ui.fragments.transfer.TransferViewEffect.NavigateBack
import com.pay.link.presentation.ui.fragments.transfer.TransferViewEffect.OnTransferSuccess
import com.pay.link.presentation.ui.fragments.transfer.TransferViewEffect.SetSourceDestinationAdapters
import com.pay.link.presentation.ui.fragments.transfer.TransferViewEffect.ShowErrorSnackBar
import com.pay.link.presentation.ui.fragments.transfer.TransferViewEffect.ShowSuccessSnackBar
import com.pay.link.presentation.ui.fragments.transfer.TransferViewEffect.ShowTransferConfirmationBottomSheet
import com.pay.link.presentation.ui.fragments.transfer.TransferViewEvent.OnAmountToTransferChanged
import com.pay.link.presentation.ui.fragments.transfer.TransferViewEvent.OnBackClicked
import com.pay.link.presentation.ui.fragments.transfer.TransferViewEvent.OnDestinationAccountSelected
import com.pay.link.presentation.ui.fragments.transfer.TransferViewEvent.OnProceedClicked
import com.pay.link.presentation.ui.fragments.transfer.TransferViewEvent.OnSourceAccountSelected
import com.pay.link.presentation.ui.fragments.transfer.TransferViewEvent.OnTransferConfirmationClicked
import com.pay.link.presentation.ui.fragments.transfer.confirmation.TransferConfirmationBottomSheet
import com.pay.link.presentation.utils.CustomProgressDialog
import com.pay.link.presentation.utils.InformationDialog
import com.pay.link.presentation.utils.SnackBarManager
import com.pay.link.presentation.utils.base.BaseFragment
import com.pay.link.presentation.utils.sharedviewmodel.TransferSharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TransferFragment : BaseFragment<FragmentTransferBinding, TransferViewModel>(
    FragmentTransferBinding::inflate,
    TransferViewModel::class
), TransferConfirmationBottomSheet.TransferConfirmationListener {

    @Inject
    lateinit var progressDialog: CustomProgressDialog

    @Inject
    lateinit var snackBarManager: SnackBarManager

    private lateinit var sourceAdapter: ArrayAdapter<Account>
    private lateinit var destinationAdapter: ArrayAdapter<Account>

    private var transferBottomSheet: TransferConfirmationBottomSheet? = null

    private val transferSharedViewModel: TransferSharedViewModel by activityViewModels()

    @Inject
    lateinit var informationDialog: InformationDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        observeViewState()
        observeViewEffect()
    }

    private fun setListeners() {

        progressDialog.attachToLifecycle(this, requireActivity())

        binding.backImageView.setOnClickListener { viewModel.onEvent(OnBackClicked) }
        binding.proceedActionButton.setOnClickListener { viewModel.onEvent(OnProceedClicked) }

        binding.amountEditText.doOnTextChanged { text, _, _, _ ->
            viewModel.onEvent(OnAmountToTransferChanged(text.toString()))
        }

        binding.sourceAccountAutocompleteTextView.setOnItemClickListener { _, _, position, _ ->
            val selectedAccount = sourceAdapter.getItem(position)
            selectedAccount?.let {

                binding.sourceAccountBalanceTextView.text =
                    getString(R.string.balance_currency_format, it.balance)

                binding.sourceAccountAutocompleteTextView.setText(
                    getString(R.string.account_display_format, it.holder, it.number),
                    false
                )
                viewModel.onEvent(OnSourceAccountSelected(it))
            }
        }

        binding.destinationAccountAutocompleteTextView.setOnItemClickListener { _, _, position, _ ->
            val selectedAccount = destinationAdapter.getItem(position)
            selectedAccount?.let {
                binding.destinationAccountAutocompleteTextView.setText(
                    getString(R.string.account_display_format, it.holder, it.number),
                    false
                )
                viewModel.onEvent(
                    OnDestinationAccountSelected(it)
                )
            }
        }

    }

    private fun observeViewState() {

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.viewState.collect { viewState ->
                progressDialog.show(viewState.isLoading)

                binding.proceedActionButton.isEnabled = viewState.isTransferButtonEnabled
                binding.amountTextInputLayout.error = viewState.amountError

            }
        }

    }

    private fun observeViewEffect() {


        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.viewEffect.collect { viewEffect ->

                when (viewEffect) {
                    NavigateBack -> findNavController().navigateUp()
                    is ShowTransferConfirmationBottomSheet ->
                        showTransferConfirmationBottomSheet(
                            sourceAccount = viewEffect.sourceAccount,
                            destinationAccount = viewEffect.destinationAccount,
                            amountToTransfer = viewEffect.amountToTransfer.toString()
                        )


                    is ShowErrorSnackBar -> snackBarManager.showErrorSnackBar(
                        binding.root,
                        viewEffect.message
                    )

                    is ShowSuccessSnackBar -> snackBarManager.showSuccessSnackBar(
                        binding.root,
                        viewEffect.message
                    )

                    is SetSourceDestinationAdapters -> setupAdapters(viewEffect.accounts)
                    is OnTransferSuccess -> {
                        showDialog("Transaction Successful", getString(R.string.transfer_success))
                    }
                }
            }
        }
    }

    private fun setupAdapters(accounts: List<Account>) {

        sourceAdapter = AccountAutoCompleteAdapter(requireContext(), accounts)
        destinationAdapter = AccountAutoCompleteAdapter(requireContext(), accounts)

        binding.sourceAccountAutocompleteTextView.setAdapter(sourceAdapter)
        binding.destinationAccountAutocompleteTextView.setAdapter(destinationAdapter)
    }


    private fun showTransferConfirmationBottomSheet(
        sourceAccount: Account,
        destinationAccount: Account,
        amountToTransfer: String
    ) {
        transferBottomSheet = TransferConfirmationBottomSheet.newInstance(
            amount = amountToTransfer,
            sourceAccount = sourceAccount,
            destinationAccount = destinationAccount
        )
        transferBottomSheet!!.setTransferConfirmationListener(this)
        transferBottomSheet?.show(parentFragmentManager, "TransferBottomSheet")
    }

    override fun onTransferConfirmed() {
        viewModel.onEvent(OnTransferConfirmationClicked)
    }

    private fun showDialog(title: String, message: String) {

        informationDialog.showDialog(
            title = title,
            subTitle = message,
            positiveButtonText = getString(R.string.ok),
            negativeButtonText = null,
            showNegativeButton = false,
            lifecycleOwner = this,
            callback = object : InformationDialog.DialogCallback {
                override fun onPositiveClick() {
                    super.onPositiveClick()
                    transferSharedViewModel.notifyTransferSuccess()
                    transferBottomSheet?.closeBottomSheet()
                    findNavController().navigateUp()
                }
            },
            )
    }

}