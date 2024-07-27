package app.s2c.data.error

open class ExitProgramException(
    val errorCode: ErrorCode,
    message: String,
    throwable: Throwable? = null,
) : Exception(message, throwable)
