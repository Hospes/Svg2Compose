package app.s2c.data.error

class ParserException(
    val errorCode: ErrorCode,
    message: String,
    cause: Throwable? = null,
) : Exception(message, cause)
