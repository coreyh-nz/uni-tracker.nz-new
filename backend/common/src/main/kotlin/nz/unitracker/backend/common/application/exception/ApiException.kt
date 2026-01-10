package nz.unitracker.backend.common.application.exception

import nz.unitracker.backend.common.application.exception.error.ErrorCode

/**
 * Base type for all application-level exceptions thrown by the backend.
 *
 * @param code Machine-readable code of the error
 * @param message Human-readable description of the error.
 */
sealed class ApiException(
    val code: ErrorCode,
    override val message: String,
) : RuntimeException(message)
