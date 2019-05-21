package exceptions

import javax.servlet.http.HttpServletResponse

abstract class ApiException(override val message: String, val httpErrorStatusCode: Int) : RuntimeException(message)
// Only the exceptions below will be returned by API calls
class CanNotFindPathException(path: String) : ApiException(path, HttpServletResponse.SC_NOT_FOUND)

class CanNotFindAccountByIdException(accountId: Long) : ApiException(accountId.toString(), HttpServletResponse.SC_NOT_FOUND)
class InsufficientOrderedBalanceToConductTransferException
    : ApiException("Orderer does not have enough money to conduct operation", HttpServletResponse.SC_FORBIDDEN)

class IllegalParameterValueException(parameterName: String) : ApiException(parameterName, HttpServletResponse.SC_BAD_REQUEST)
class ParameterMissedException(parameterName: String) : ApiException(parameterName, HttpServletResponse.SC_BAD_REQUEST)
class IllegalAccountStateException(description: String) : ApiException(description, HttpServletResponse.SC_BAD_REQUEST)

// Only the cause for that exception is being logged
// And the cause will never be shown to the API user (due to security risk)
class SomethingWentWrong : ApiException(
        "Something went wrong! We do investigate on the issue!",
        HttpServletResponse.SC_INTERNAL_SERVER_ERROR
)

