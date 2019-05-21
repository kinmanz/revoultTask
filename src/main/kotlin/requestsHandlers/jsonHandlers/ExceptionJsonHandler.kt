package requestsHandlers.jsonHandlers

import exceptions.ApiException
import javax.servlet.http.HttpServletResponse

class ExceptionJsonHandler(private val exception: ApiException) : BasicJsonHandler() {

    override fun setResponseStatus(response: HttpServletResponse) {
        response.status = exception.httpErrorStatusCode
    }

    override fun getContentObject(requestParams: Map<String, String>, response: HttpServletResponse) = mapOf(
            "exception" to exception.toString()
    )
}