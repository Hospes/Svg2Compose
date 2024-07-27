package app.s2c.data.error

class MissingDependencyException(
    errorCode: ErrorCode,
    message: String,
    throwable: Throwable? = null,
) : ExitProgramException(errorCode, message, throwable)
