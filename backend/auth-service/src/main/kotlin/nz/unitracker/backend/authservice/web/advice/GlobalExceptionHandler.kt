package nz.unitracker.backend.authservice.web.advice

import nz.unitracker.backend.authservice.application.exception.UserEmailAlreadyExistsException
import nz.unitracker.backend.common.web.dto.ApiErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(UserEmailAlreadyExistsException::class)
    fun handleUserEmailAlreadyExistsException(exception: UserEmailAlreadyExistsException): ResponseEntity<ApiErrorResponse> {
        val code = exception.code
        val message = exception.message
        val httpCode = HttpStatus.CONFLICT

        return ResponseEntity
            .status(httpCode)
            .body(
                ApiErrorResponse(
                    code = code.code,
                    message = message,
                    fields = exception.fields,
                ),
            )
    }
}
