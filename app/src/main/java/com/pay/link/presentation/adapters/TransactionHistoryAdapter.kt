package com.pay.link.presentation.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pay.link.databinding.ItemTransactionHistoryBinding
import com.pay.link.domain.models.TransactionWithAccountNames
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TransactionHistoryAdapter(
) : RecyclerView.Adapter<TransactionHistoryAdapter.TransactionViewHolder>() {

    private var transactions: List<TransactionWithAccountNames> = emptyList()


    inner class TransactionViewHolder(val binding: ItemTransactionHistoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTransactionHistoryBinding.inflate(inflater, parent, false)
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = transactions[position]
        with(holder.binding) {
            sourceAccountName.text = transaction.sourceAccountName
            sourceAccountNumber.text = transaction.sourceAccountNumber
            destinationAccountName.text = transaction.destinationAccountName
            destinationAccountNumber.text = transaction.destinationAccountNumber
            val currencyFormat = NumberFormat.getCurrencyInstance(Locale.US).apply {
                maximumFractionDigits = 2
                minimumFractionDigits = 2
            }
            transactionAmount.text = currencyFormat.format(transaction.amount)

            // Will be useful when we know amount was incoming or outgoing
//            transactionAmount.setTextColor(
//                if (transaction.amount > 0) Color.GREEN else Color.RED
//            )
            transactionDateTime.text = formatDate(transaction.timestamp)
        }
    }

    override fun getItemCount(): Int = transactions.size

    private fun formatDate(timestamp: Long): String {
        val sdf = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }

    fun updateData(newTransactions: List<TransactionWithAccountNames>) {
        transactions = newTransactions
        // Since not change we can do this but Diff Util is prefered same as accounts Adapter
        notifyDataSetChanged()
    }
}
