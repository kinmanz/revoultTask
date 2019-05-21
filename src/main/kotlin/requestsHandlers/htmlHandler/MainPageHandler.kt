package requestsHandlers.htmlHandler

import requestsHandlers.BasicHandler
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.servlet.http.HttpServletResponse


object MainPageHandler : BasicHandler() {

    private val mainPageContent = this.javaClass.classLoader.getResourceAsStream("index.html")!!.let {
        BufferedReader(InputStreamReader(it))
    }.readText()

    override fun getContent(requestParams: Map<String, String>, response: HttpServletResponse) = mainPageContent

    override fun setResponseAttributes(response: HttpServletResponse) {
        response.contentType = "text/html;charset=utf-8"
        response.status = HttpServletResponse.SC_OK
    }
}