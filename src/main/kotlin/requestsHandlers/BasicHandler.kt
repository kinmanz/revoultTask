package requestsHandlers

import exceptions.IllegalParameterValueException
import exceptions.ParameterMissedException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

abstract class BasicHandler {
    abstract fun getContent(requestParams: Map<String, String>, response: HttpServletResponse): String
    abstract fun setResponseAttributes(response: HttpServletResponse)
    open fun setResponseStatus(response: HttpServletResponse) {
        response.status = HttpServletResponse.SC_OK
    }

    fun updateResponse(
            request: HttpServletRequest,
            response: HttpServletResponse,
            requiredParameters: List<String> = emptyList()
    ) {
        checkRequiredParameterProvided(request.parameterMap, requiredParameters)
        response.writer.println(
                getContent(
                        requestParams = request.parameterMap.entries.map { it.key to it.value.first() }.toMap(),
                        response = response
                ).toString()
        )
        setResponseAttributes(response)
        setResponseStatus(response)
    }

    private fun checkRequiredParameterProvided(
            requestParams: MutableMap<String, Array<String>>,
            requiredParameters: List<String> = emptyList()
    ) {
        val missingParameter = requiredParameters.firstOrNull { it !in requestParams.keys }
        if (missingParameter != null)
            throw ParameterMissedException(missingParameter)
        val brokenParameter = requiredParameters.firstOrNull { requestParams[it]!!.size > 1 }
        if (brokenParameter != null)
            throw IllegalParameterValueException(brokenParameter)
    }
}