package com.pay.link.data.repositories.account

import com.pay.link.data.local.dao.AccountDao
import com.pay.link.data.local.entities.AccountEntity
import com.pay.link.domain.models.Account
import com.pay.link.domain.repository.AccountRepository
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(private val accountDao: AccountDao) :
    AccountRepository {

    override fun getAccounts(): List<Account> {
        return accountDao.getAllAccounts().map { account ->
            Account(account.id, account.accountHolder, account.accountNumber, account.balance)
        }
    }

    override suspend fun getAccountByNumber(accountNumber: String): Account? {
        return accountDao.getAccountByNumber(accountNumber)?.let {
            Account(it.id, it.accountHolder, it.accountNumber, it.balance)
        }
    }

    override suspend fun updateAccount(account: Account) {
        accountDao.updateAccount(
            AccountEntity(
                account.id,
                account.holder,
                account.number,
                account.balance
            )
        )
    }

    override suspend fun updateBalance(accountId: Int, newBalance: Double) {
        accountDao.updateBalance(accountId, newBalance)
    }

    override suspend fun mockData(account: Account) {
        accountDao.createAccount(
            AccountEntity(
                accountHolder = account.holder,
                accountNumber = account.number,
                balance = account.balance
            )
        )
    }

    override suspend fun deleteAccounts() {
        accountDao.deleteAllAccounts()
    }
}
