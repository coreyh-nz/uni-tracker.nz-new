package nz.unitracker.backend.common.web.advice

import io.github.oshai.kotlinlogging.KotlinLogging
import nz.unitracker.backend.common.application.exception.ApiException
import nz.unitracker.backend.common.application.exception.FieldValidationException
import nz.unitracker.backend.common.application.exception.FieldViolation
import nz.unitracker.backend.common.application.exception.error.ErrorCode
import nz.unitracker.backend.common.web.dto.ApiErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * Global exception handler for the application.
 *
 * Handles both expected API exceptions ([ApiException]) and unexpected exceptions.
 */
@RestControllerAdvice
class GlobalExceptionHandler {
    private val kLogger = KotlinLogging.logger {}

    /**
     * Handles all [FieldValidationException]s thrown by the application.
     *
     * @param exception the API exception that was thrown
     * @return a [ResponseEntity] containing an [ApiErrorResponse] with the error code, message, and field violations.
     */
    @ExceptionHandler(FieldValidationException::class)
    fun handleFieldValidationException(exception: FieldValidationException): ResponseEntity<ApiErrorResponse> {
        val code = exception.code
        val fields = exception.fields
        kLogger.debug {
            "Encountered validation exception: code=${code.code}, fields=$fields"
        }

        return toResponseEntity(
            httpStatus = code.httpStatus,
            code = code,
            message = exception.message,
            fields = fields,
        )
    }

    /**
     * Handles all [ApiException]s thrown by the application.
     *
     * @param exception the API exception that was thrown
     * @return a [ResponseEntity] containing an [ApiErrorResponse] with the error code and message
     */
    @ExceptionHandler(ApiException::class)
    fun handleApiException(exception: ApiException): ResponseEntity<ApiErrorResponse> {
        val code = exception.code
        if (code is ErrorCode.Module) {
            kLogger.error(exception) { "Unhandled module-specific error: ${code.code}" }
            return toResponseEntity(
                httpStatus = ErrorCode.Generic.INTERNAL.httpStatus,
                code = ErrorCode.Generic.INTERNAL,
                message = HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase,
            )
        }
        val message =
            if (code == ErrorCode.Generic.INTERNAL) {
                logUnexpectedError(exception)
                HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase
            } else {
                kLogger.debug { "Encountered API exception: code=${code.code}, message=${exception.message}" }
                exception.message
            }
        return toResponseEntity(
            httpStatus = code.httpStatus,
            code = code,
            message = message,
        )
    }

    /**
     * Handles all other unexpected exceptions.
     *
     * @param exception the unexpected exception that was thrown
     * @return a [ResponseEntity] containing an [ApiErrorResponse] with a generic internal error code
     */
    @ExceptionHandler(Exception::class)
    fun handleUnexpectedException(exception: Exception): ResponseEntity<ApiErrorResponse> {
        logUnexpectedError(exception)

        return toResponseEntity(
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
            code = ErrorCode.Generic.INTERNAL,
            message = HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase,
        )
    }

    private fun logUnexpectedError(exception: Exception) {
        kLogger.error(exception) { "Unhandled exception" }
    }

    private fun toResponseEntity(
        httpStatus: HttpStatus,
        code: ErrorCode,
        message: String,
        fields: Map<String, FieldViolation>? = null,
    ): ResponseEntity<ApiErrorResponse> =
        ResponseEntity
            .status(httpStatus)
            .body(
                ApiErrorResponse(
                    code = code.code,
                    message = message,
                    fields = fields,
                ),
            )
}
