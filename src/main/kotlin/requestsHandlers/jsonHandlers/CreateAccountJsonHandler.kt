package requestsHandlers.jsonHandlers

import exceptions.IllegalParameterValueException
import requestsHandlers.entities.Account
import services.AccountService
import javax.servlet.http.HttpServletResponse

object CreateAccountJsonHandler : BasicJsonHandler() {
    // positive number pattern
    private val numberPattern = Regex("\\d+")

    override fun getContentObject(requestParams: Map<String, String>, response: HttpServletResponse): Account {
        val name = requestParams["name"]!!
        val balance = requestParams["balance"]!!
        if (!balance.matches(numberPattern))
            throw IllegalParameterValueException("balance")
        return AccountService.createNewAccount(name, balance.toLong())
    }
}