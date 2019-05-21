package servlets

import exceptions.ApiException
import exceptions.CanNotFindPathException
import requestsHandlers.htmlHandler.MainPageHandler
import requestsHandlers.jsonHandlers.*
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class AllRequestsServlet : HttpServlet() {

    public override fun doGet(
            request: HttpServletRequest,
            response: HttpServletResponse
    ) {
        val pathInfo = request.pathInfo
        logInfo("Attempt to access path : $pathInfo")
        try {
            // URL mapping
            when (pathInfo) {
                "/" -> MainPageHandler.updateResponse(request, response)
                "/api/listAccounts" -> ListAccountsJsonHandler.updateResponse(request, response)
                "/api/account" -> GetAccountInfoJsonHandler.updateResponse(request, response, requiredParameters = listOf("id"))
                "/api/createAccount" -> CreateAccountJsonHandler.updateResponse(request, response, requiredParameters = listOf("name", "balance"))
                "/api/transfer" -> TransferMoneyJsonHandler.updateResponse(request, response, requiredParameters = listOf("fromId", "toId", "amount"))
                else -> {
                    throw CanNotFindPathException(pathInfo)
                }
            }
        } catch (exp: ApiException) {
            request.setAttribute("exception", exp)
            ExceptionJsonHandler(exp).updateResponse(request, response)
        } catch (exp: Exception) {
            exp.printStackTrace()
        }
    }

    // put any logging impl here
    fun logInfo(message: String) {
        println(message)
    }
}
