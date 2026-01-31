package nz.unitracker.backend.authservice.web.validation

import io.konform.validation.Validation
import nz.unitracker.backend.authservice.application.validation.UserValidationRules.emailRules
import nz.unitracker.backend.authservice.application.validation.UserValidationRules.passwordRules
import nz.unitracker.backend.authservice.web.dto.LoginRequest
import nz.unitracker.backend.authservice.web.validation.LoginRequestValidator.loginRequestValidator
import nz.unitracker.backend.common.application.validation.ensureValid

object LoginRequestValidator {
    val loginRequestValidator =
        Validation {
            LoginRequest::email { emailRules() }
            LoginRequest::password { passwordRules(LoginRequest::password) }
        }
}

fun LoginRequest.validate(): LoginRequest =
    normalise().apply {
        ensureValid(loginRequestValidator)
    }
