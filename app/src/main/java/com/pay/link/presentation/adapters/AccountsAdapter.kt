package com.pay.link.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pay.link.R
import com.pay.link.databinding.ItemAccountBinding
import com.pay.link.domain.models.Account
import com.pay.link.domain.models.BankName
import java.text.NumberFormat
import java.util.Locale

class AccountsAdapter(
    private val onItemClick: (Account) -> Unit
) : ListAdapter<Account, AccountsAdapter.AccountViewHolder>(AccountDiffCallback()) {

    inner class AccountViewHolder(private val binding: ItemAccountBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(account: Account) {
            binding.apply {
                accountHolderValue.text = account.holder
                accountNumberValue.text = account.number
                binding.bankIcon.setImageResource(BankName.getBankLogo(account.bankName))

                val currencyFormat = NumberFormat.getCurrencyInstance(Locale.US).apply {
                    maximumFractionDigits = 2
                    minimumFractionDigits = 2
                }
                accountBalanceValue.text = currencyFormat.format(account.balance)

                root.setOnClickListener { onItemClick(account) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val binding = ItemAccountBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AccountViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun updateData(newList: List<Account>) {
        submitList(newList)
    }

}

class AccountDiffCallback : DiffUtil.ItemCallback<Account>() {
    override fun areItemsTheSame(oldItem: Account, newItem: Account): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Account, newItem: Account): Boolean {
        return oldItem == newItem
    }
}
