package app.s2c.data.error

class OptimizationException(
    val errorCode: ErrorCode,
    message: String = errorCode.name,
    throwable: Throwable? = null,
) : Exception(message, throwable)
