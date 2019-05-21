package services

import exceptions.CanNotFindAccountByIdException
import requestsHandlers.entities.Account
import java.util.*
import java.util.concurrent.ConcurrentHashMap

object AccountService {
    private val accountMap = ConcurrentHashMap<Long, Account>()

    // prepopulate accounts
    init {
        listOf(
                Account("John", 100),
                Account("Tom", 200),
                Account("Matthew", 300)
        ).forEach { account ->
            accountMap[account.id] = account
        }
    }

    /**
     * @return list of all available accounts
     */
    val allAccounts: List<Account>
        get() = ArrayList(accountMap.values)

    fun getAccountById(accountId: Long) = accountMap[accountId] ?: throw CanNotFindAccountByIdException(accountId)

    /**
     * Creates new account with name == accountName and balance == accountBalance
     *
     * @return newly created account id
     */
    fun createNewAccount(accountName: String, accountBalance: Long) = Account(accountName, accountBalance)
            .also { account -> accountMap[account.id] = account }
}
