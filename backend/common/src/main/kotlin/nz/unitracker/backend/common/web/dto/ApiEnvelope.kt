package nz.unitracker.backend.common.web.dto

import nz.unitracker.backend.common.application.exception.FieldViolation

sealed interface ApiEnvelope

/**
 * Represents a successful API response containing optional data.
 *
 * @param T The type of the data payload.
 * @property data The actual response data, or `null` if no content is returned.
 */
data class ApiResponse<T>(
    val data: T? = null,
) : ApiEnvelope

/**
 * Represents a standardized error response returned by the API.
 *
 * @property code A custom application-level error code for programmatic handling.
 * @property message A brief description of the error type.
 * @property fields An optional map of field-level validation errors, where the key is the field name
 * and the value describes the violations for that field.
 */
data class ApiErrorResponse(
    val code: String,
    val message: String? = null,
    val fields: Map<String, FieldViolation>? = null,
) : ApiEnvelope
