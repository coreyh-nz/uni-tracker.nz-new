package nz.unitracker.backend.authservice.application.exception

import nz.unitracker.backend.authservice.application.validation.UserValidationMessages
import nz.unitracker.backend.common.application.exception.FieldValidationException
import nz.unitracker.backend.common.application.exception.FieldViolation

class UserEmailAlreadyExistsException :
    FieldValidationException(
        code = AuthErrorCode.EMAIL_ALREADY_USED,
        fields =
            mapOf(
                "email" to
                    FieldViolation(violations = listOf(UserValidationMessages.EMAIL_ALREADY_IN_USE)),
            ),
    )
