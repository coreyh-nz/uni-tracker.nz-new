package nz.unitracker.backend.authservice.application.exception

import nz.unitracker.backend.common.application.exception.error.ErrorCode
import org.springframework.http.HttpStatus

enum class AuthErrorCode(
    override val code: String,
    override val httpStatus: HttpStatus,
) : ErrorCode.Module {
    EMAIL_ALREADY_USED(
        code = "auth.email_already_used",
        httpStatus = ErrorCode.Generic.VALIDATION_VIOLATION.httpStatus,
    ),
}
