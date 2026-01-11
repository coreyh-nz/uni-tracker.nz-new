package nz.unitracker.backend.common.application.exception.error

import org.springframework.http.HttpStatus

/**
 * Represents machine-readable error codes.
 */
sealed interface ErrorCode {
    /** A unique string identifier for the error code. */
    val code: String

    /** The HTTP status associated with this error. */
    val httpStatus: HttpStatus

    /**
     * Generic error codes shared between services.
     */
    enum class Generic(
        override val code: String,
        override val httpStatus: HttpStatus,
    ) : ErrorCode {
        UNAUTHENTICATED(
            code = "auth.unauthenticated",
            httpStatus = HttpStatus.UNAUTHORIZED,
        ),
        FORBIDDEN(
            code = "auth.forbidden",
            httpStatus = HttpStatus.FORBIDDEN,
        ),
        NOT_FOUND(
            code = "not_found",
            httpStatus = HttpStatus.NOT_FOUND,
        ),
        VALIDATION_VIOLATION(
            code = "validation.violation",
            httpStatus = HttpStatus.BAD_REQUEST,
        ),
        INTERNAL(
            code = "internal",
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
        ),
    }

    /**
     * Marker interface for module-specific error codes.
     *
     * Modules should define their own HTTP status explicitly.
     */
    interface Module : ErrorCode
}
