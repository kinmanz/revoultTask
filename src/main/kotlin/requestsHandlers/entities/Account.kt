package requestsHandlers.entities

import exceptions.IllegalAccountStateException
import exceptions.IllegalParameterValueException
import exceptions.InsufficientOrderedBalanceToConductTransferException
import net.jcip.annotations.GuardedBy
import net.jcip.annotations.ThreadSafe
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicLong

@ThreadSafe
class Account(val accountName: String, balance: Long) {
    // locked until initialization completion
    private val accountLocked = AtomicBoolean(true)

    companion object {
        @JvmStatic
        val idGenerator = AtomicLong(1)
    }

    val id = idGenerator.getAndIncrement()
    @Volatile
    var balance: Long = balance
        private set

    init {
        if (balance < 0)
            throw IllegalAccountStateException("Balance can not be less than 0!")
        if (accountName.isBlank())
            throw IllegalAccountStateException("Account name can not be blank!")
        // release lock, here we sure that balance value have been initialized (in case of escaped ref to constructed instance)
        accountLocked.releaseLock()
    }

    @GuardedBy("accountLocked")
    fun lockAndWithdraw(amount: Long) {
        if (amount < 0)
            throw IllegalParameterValueException(amount.toString())
        if (amount == 0L)
            return
        accountLocked.acquireLock()
        try {
            if (balance - amount >= 0)
                balance -= amount
            else
                throw InsufficientOrderedBalanceToConductTransferException()
        } finally {
            accountLocked.releaseLock()
        }
    }

    @GuardedBy("accountLocked")
    fun lockAndReplenish(amount: Long) {
        if (amount < 0)
            throw IllegalParameterValueException(amount.toString())
        if (amount == 0L)
            return
        accountLocked.acquireLock()
        balance += amount
        accountLocked.releaseLock()
    }

    // Spin locking
    private fun AtomicBoolean.acquireLock() {
        while (!this.compareAndSet(false, true)) {
        }
    }

    private fun AtomicBoolean.releaseLock() = this.set(false)
}

