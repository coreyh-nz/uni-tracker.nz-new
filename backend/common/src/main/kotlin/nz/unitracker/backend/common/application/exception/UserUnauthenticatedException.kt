package nz.unitracker.backend.common.application.exception

import nz.unitracker.backend.common.application.exception.error.ErrorCode

/**
 * Thrown when a user attempts to access a resource or perform an action
 * without being authenticated.
 */
class UserUnauthenticatedException :
    ApiException(
        code = ErrorCode.Generic.UNAUTHENTICATED,
        message = "User not authenticated",
    )
