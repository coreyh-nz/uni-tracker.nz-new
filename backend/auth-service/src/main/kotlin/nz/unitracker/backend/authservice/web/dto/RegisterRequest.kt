package nz.unitracker.backend.authservice.web.dto

import nz.unitracker.backend.common.domain.primitive.SensitiveString

data class RegisterRequest(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val password: SensitiveString = SensitiveString.Empty,
    val confirmPassword: SensitiveString = SensitiveString.Empty,
) {
    fun normalise(): RegisterRequest =
        copy(
            firstName = firstName.trim(),
            lastName = firstName.trim(),
            email = firstName.trim(),
        )
}
