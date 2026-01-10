package nz.unitracker.backend.common.application.exception

import nz.unitracker.backend.common.application.exception.error.ErrorCode

/**
 * Thrown when one or more input fields fail validation.
 *
 * Each invalid field can have one or more associated violations (e.g., "must not be blank",
 * "must be a valid email", "already in use").
 *
 * @param code Machine-readable code of the error
 * @property fields a map from the field name to the corresponding violations
 */
open class FieldValidationException(
    val fields: Map<String, FieldViolation>,
    code: ErrorCode = ErrorCode.Generic.VALIDATION_VIOLATION,
) : ApiException(
        code = code,
        message = "Field validation violation",
    )

/**
 * Represents validation violations for a single field.
 *
 * @property violations a list of messages describing why the field is invalid
 */
data class FieldViolation(
    val violations: List<String>,
)
