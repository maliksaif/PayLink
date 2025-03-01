package com.pay.link.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import android.widget.ArrayAdapter
import com.pay.link.R
import com.pay.link.databinding.ItemAutoCompleteAccountBinding
import com.pay.link.domain.models.Account

class AccountAutoCompleteAdapter(
    context: Context,
    private val accountList: List<Account>
) : ArrayAdapter<Account>(context, 0, accountList), Filterable {

    private var filteredAccounts: List<Account> = accountList

    override fun getCount(): Int = filteredAccounts.size

    override fun getItem(position: Int): Account = filteredAccounts[position]

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: ItemAutoCompleteAccountBinding

        if (convertView == null) {
            val inflater = LayoutInflater.from(context)
            binding = ItemAutoCompleteAccountBinding.inflate(inflater, parent, false)
        } else {
            binding = ItemAutoCompleteAccountBinding.bind(convertView)
        }

        val account = getItem(position)
        binding.accountHolder.text = account.holder
        binding.accountNumber.text = account.number

        return binding.root
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query = constraint?.toString()?.lowercase()?.trim()
                val results = FilterResults()

                results.values = if (query.isNullOrEmpty()) {
                    accountList
                } else {
                    accountList.filter {
                        it.holder.lowercase().contains(query) || it.number.contains(query)
                    }
                }
                results.count = (results.values as List<Account>).size
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredAccounts = results?.values as List<Account>
                notifyDataSetChanged()
            }
        }
    }
}
