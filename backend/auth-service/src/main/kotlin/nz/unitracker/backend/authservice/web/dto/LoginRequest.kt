package nz.unitracker.backend.authservice.web.dto

import nz.unitracker.backend.common.domain.primitive.SensitiveString

data class LoginRequest(
    val email: String = "",
    val password: SensitiveString = SensitiveString.Empty,
) {
    fun normalise(): LoginRequest = copy(email = email.trim())
}
