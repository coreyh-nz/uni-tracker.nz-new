package nz.unitracker.backend.common.application.exception

import nz.unitracker.backend.common.application.exception.error.ErrorCode

/**
 * Thrown when an authenticated user attempts to perform an action
 * they do not have permission for.
 */
class UserForbiddenException(
    message: String = "User not authorised to perform this action",
) : ApiException(
        code = ErrorCode.Generic.FORBIDDEN,
        message = message,
    )
