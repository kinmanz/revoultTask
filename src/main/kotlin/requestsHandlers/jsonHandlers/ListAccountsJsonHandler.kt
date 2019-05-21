package requestsHandlers.jsonHandlers

import services.AccountService
import javax.servlet.http.HttpServletResponse

object ListAccountsJsonHandler : BasicJsonHandler() {
    override fun getContentObject(requestParams: Map<String, String>, response: HttpServletResponse)
            = AccountService.allAccounts
}