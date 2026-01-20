package nz.unitracker.backend.authservice.web.dto

data class LoginRequest(
    val email: String,
    val password: String,
)
