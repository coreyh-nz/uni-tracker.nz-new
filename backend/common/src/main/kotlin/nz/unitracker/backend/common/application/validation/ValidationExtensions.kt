package nz.unitracker.backend.common.application.validation

import io.konform.validation.Validation
import nz.unitracker.backend.common.application.exception.FieldValidationException
import nz.unitracker.backend.common.application.exception.FieldViolation

/**
 * Validates the receiver object using the provided [validation] rules.
 *
 * This is an extension function that can be called on any type [T]. It will run the
 * given [validation] and, if there are any violations, throw a [FieldValidationException]
 * containing a map of field names to their corresponding [FieldViolation]s.
 *
 * @param T the type of the object being validated
 * @param validation the [Validation] rules to apply to the object
 * @throws FieldValidationException if validation fails, containing details of the fields
 *         that did not pass validation
 */
fun <T> T.ensureValid(validation: Validation<T>) {
    val result = validation.validate(this)
    if (result.isValid) return

    val fieldViolations =
        result.errors
            // extract field name from Konform's dot-separated dataPath (e.g., ".name" -> "name")
            .groupBy {
                it.path.dataPath
                    .split('.')
                    .lastOrNull()
                    ?: it.path.dataPath // fallback to full path
            }.mapValues { (_, errors) ->
                FieldViolation(errors.map { it.message })
            }
    throw FieldValidationException(fieldViolations)
}
