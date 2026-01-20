package nz.unitracker.backend.authservice.helper.factory

import nz.unitracker.backend.authservice.web.dto.LoginRequest

fun createTestLoginRequest(
    email: String = "jane.doe@gmail.com",
    password: String = "Abc123!!",
) = LoginRequest(
    email = email,
    password = password,
)
