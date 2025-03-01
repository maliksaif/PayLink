package com.pay.link.domain.models

import androidx.annotation.DrawableRes
import com.pay.link.R


enum class BankName(val displayName: String, @DrawableRes val logoResId: Int) {
    AL_RAJHI("Al Rajhi Bank", R.drawable.ic_al_rajhi_bank),
    DUBAI_ISLAMIC("Dubai Islamic Bank", R.drawable.ic_dubai_islamic_bank),
    MEEZAN("Meezan Bank", R.drawable.ic_meezan),
    CHASE("Chase Bank", R.drawable.ic_chase),
    HSBC("HSBC", R.drawable.ic_placeholder),
    CITIBANK("Citibank", R.drawable.ic_placeholder),
    STANDARD_CHARTERED("Standard Chartered", R.drawable.ic_placeholder),
    BANK_OF_AMERICA("Bank of America", R.drawable.ic_placeholder),
    WELLS_FARGO("Wells Fargo", R.drawable.ic_placeholder),
    JP_MORGAN("JP Morgan Chase", R.drawable.ic_placeholder);

    companion object {
        fun getRandomBank() = entries.toTypedArray().random()
        fun getBankLogo(bankName: String): Int {
            return entries.find { it.displayName == bankName }?.logoResId ?: R.drawable.ic_placeholder
        }
    }
}
