package nz.unitracker.backend.authservice.web.validation

import io.konform.validation.Validation
import nz.unitracker.backend.authservice.application.validation.UserValidationRules.emailRules
import nz.unitracker.backend.authservice.application.validation.UserValidationRules.firstNameRules
import nz.unitracker.backend.authservice.application.validation.UserValidationRules.lastNameRules
import nz.unitracker.backend.authservice.application.validation.UserValidationRules.passwordRules
import nz.unitracker.backend.authservice.web.dto.RegisterRequest
import nz.unitracker.backend.authservice.web.validation.RegisterRequestValidator.registerRequestValidator

object RegisterRequestValidator {
    val registerRequestValidator =
        Validation {
            RegisterRequest::firstName { firstNameRules() }
            RegisterRequest::lastName { lastNameRules() }
            RegisterRequest::email { emailRules() }
            RegisterRequest::password { passwordRules(RegisterRequest::password) }
        }
}

fun RegisterRequest.validate(): RegisterRequest =
    normalise().apply {
        registerRequestValidator.validate(this)
    }
