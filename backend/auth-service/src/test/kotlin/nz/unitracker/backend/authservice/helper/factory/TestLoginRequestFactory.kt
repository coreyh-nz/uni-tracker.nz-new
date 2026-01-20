package nz.unitracker.backend.authservice.helper.factory

import nz.unitracker.backend.authservice.web.dto.LoginRequest
import nz.unitracker.backend.common.domain.primitive.SensitiveString

fun createTestLoginRequest(
    email: String = "jane.doe@gmail.com",
    password: String = "Abc123!!",
) = LoginRequest(
    email = email,
    password = SensitiveString(password),
)
