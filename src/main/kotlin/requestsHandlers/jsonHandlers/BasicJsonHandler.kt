package requestsHandlers.jsonHandlers

import com.fasterxml.jackson.databind.ObjectMapper
import requestsHandlers.BasicHandler
import javax.servlet.http.HttpServletResponse

abstract class BasicJsonHandler : BasicHandler() {

    companion object {
        @JvmStatic
        var objectMapper = ObjectMapper()

        private fun toJsonString(contentObject: Any) = objectMapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(contentObject)!!

    }

    override fun setResponseAttributes(response: HttpServletResponse) {
        response.contentType = "application/json;charset=utf-8"
    }

    override fun getContent(requestParams: Map<String, String>, response: HttpServletResponse)
            = toJsonString(getContentObject(requestParams, response))

    abstract fun getContentObject(requestParams: Map<String, String>, response: HttpServletResponse): Any
}