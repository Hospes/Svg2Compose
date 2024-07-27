package app.s2c.core.base.base

sealed interface InvokeStatus {
    data object Started : InvokeStatus
    data object Success : InvokeStatus
    data class Error(val throwable: Throwable) : InvokeStatus
}