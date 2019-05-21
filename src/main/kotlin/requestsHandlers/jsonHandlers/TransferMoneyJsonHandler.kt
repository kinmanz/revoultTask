package requestsHandlers.jsonHandlers

import exceptions.IllegalParameterValueException
import services.AccountService
import javax.servlet.http.HttpServletResponse

object TransferMoneyJsonHandler : BasicJsonHandler() {
    // positive number pattern
    private val numberPattern = Regex("\\d+")

    override fun getContentObject(requestParams: Map<String, String>, response: HttpServletResponse): Any {
        val fromId = requestParams["fromId"]!!
        val toId = requestParams["toId"]!!
        val amount = requestParams["amount"]!!
        listOf(
                fromId to "fromId",
                toId to "toId",
                amount to "amount"
        ).forEach { (paramValue, paramName) ->
            if (!paramValue.matches(numberPattern))
                throw IllegalParameterValueException(paramName)
        }
        AccountService.getAccountById(fromId.toLong()).lockAndWithdraw(amount.toLong())
        AccountService.getAccountById(toId.toLong()).lockAndReplenish(amount.toLong())
        return mapOf("status" to "payment conducted")
    }
}