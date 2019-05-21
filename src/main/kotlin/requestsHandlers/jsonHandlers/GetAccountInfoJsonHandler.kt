package requestsHandlers.jsonHandlers

import exceptions.IllegalParameterValueException
import requestsHandlers.entities.Account
import services.AccountService
import javax.servlet.http.HttpServletResponse

object GetAccountInfoJsonHandler : BasicJsonHandler() {
    // positive number pattern
    private val numberPattern = Regex("\\d+")

    override fun getContentObject(requestParams: Map<String, String>, response: HttpServletResponse): Account {
        val id = requestParams["id"]!!
        if (!id.matches(numberPattern))
            throw IllegalParameterValueException("id")
        return AccountService.getAccountById(id.toLong())
    }
}