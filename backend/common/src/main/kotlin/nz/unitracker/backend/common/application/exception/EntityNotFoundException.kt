package nz.unitracker.backend.common.application.exception

import nz.unitracker.backend.common.application.exception.error.ErrorCode

/**
 * Thrown when a requested entity cannot be found in persistent storage.
 *
 * @param message A descriptive message explaining which entity was not found.
 */
open class EntityNotFoundException(
    message: String,
) : ApiException(
        code = ErrorCode.Generic.NOT_FOUND,
        message = message,
    )
