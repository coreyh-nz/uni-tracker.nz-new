package nz.unitracker.backend.authservice.web.dto

import nz.unitracker.backend.common.domain.primitive.SensitiveString

data class RegisterRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: SensitiveString,
    val confirmPassword: SensitiveString,
)
