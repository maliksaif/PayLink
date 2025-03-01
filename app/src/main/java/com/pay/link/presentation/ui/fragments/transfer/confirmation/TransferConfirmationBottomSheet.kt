package com.pay.link.presentation.ui.fragments.transfer.confirmation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.BundleCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.pay.link.R
import com.pay.link.databinding.BottomSheetTransferConfirmationBinding
import com.pay.link.domain.models.Account
import java.text.NumberFormat
import java.util.Locale


class TransferConfirmationBottomSheet : BottomSheetDialogFragment() {

    private var _binding: BottomSheetTransferConfirmationBinding? = null
    private val binding get() = _binding!!

    private var amount: String? = null
    private var sourceAccount: Account? = null
    private var destinationAccount: Account? = null

    private var listener: TransferConfirmationListener? = null

    fun setTransferConfirmationListener(listener: TransferConfirmationListener) {
        this.listener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setStyle(STYLE_NO_TITLE, R.style.BottomSheetDialogTheme)
        super.onCreate(savedInstanceState)

        arguments?.let {
            amount = it.getString(ARG_AMOUNT)
            sourceAccount = BundleCompat.getSerializable(it,ARG_SOURCE_ACCOUNT, Account::class.java)
            destinationAccount = BundleCompat.getSerializable(it, ARG_DESTINATION_ACCOUNT, Account::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetTransferConfirmationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currencyFormat = NumberFormat.getCurrencyInstance(Locale.US).apply {
            maximumFractionDigits = 2
            minimumFractionDigits = 2
        }
        binding.amountTextViewValue.text = currencyFormat.format(amount?.toDoubleOrNull() ?: 0.0)

        sourceAccount?.let {
            binding.sourceAccountNameValue.text = it.holder
            binding.sourceAccountNumberValue.text = it.number
            // Future enhancements
//            binding.sourceAccountLogo.setImageResource(it.bankLogo)
        }

        destinationAccount?.let {
            binding.destinationAccountNameValue.text = it.holder
            binding.destinationAccountNumberValue.text = it.number
//            binding.destinationAccountLogo.setImageResource(it.bankLogo)
        }

        // Notify the fragment when confirm button is clicked
        binding.confirmTransferActionButton.setOnClickListener {
            listener?.onTransferConfirmed()
        }

        binding.closeImageView.setOnClickListener {
            closeBottomSheet()
        }
    }

    fun closeBottomSheet() {
        dismiss()
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_AMOUNT = "arg_amount"
        private const val ARG_SOURCE_ACCOUNT = "arg_source_account"
        private const val ARG_DESTINATION_ACCOUNT = "arg_destination_account"

        fun newInstance(amount: String, sourceAccount: Account, destinationAccount: Account): TransferConfirmationBottomSheet {
            val fragment = TransferConfirmationBottomSheet()
            val args = Bundle().apply {
                putString(ARG_AMOUNT, amount)
                putSerializable(ARG_SOURCE_ACCOUNT, sourceAccount)
                putSerializable(ARG_DESTINATION_ACCOUNT, destinationAccount)
            }
            fragment.arguments = args
            return fragment
        }
    }

    interface TransferConfirmationListener {
        fun onTransferConfirmed()
    }
}

