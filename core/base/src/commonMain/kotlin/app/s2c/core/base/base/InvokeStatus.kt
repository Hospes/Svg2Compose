package app.s2c.core.base.base

/**
 * Represents the status of an operation or function invocation.
 *
 * This sealed interface provides a type-safe way to track the lifecycle
 * of operations, from initiation through completion or failure.
 */
sealed interface InvokeStatus {
    /**
     * Indicates that an operation has started.
     */
    data object Started : InvokeStatus
    /**
     * Indicates that an operation has completed successfully.
     */
    data object Success : InvokeStatus
    /**
     * Represents an error that occurred during an operation.
     *
     * @property throwable the Throwable that caused the error
     */
    data class Error(val throwable: Throwable) : InvokeStatus
}