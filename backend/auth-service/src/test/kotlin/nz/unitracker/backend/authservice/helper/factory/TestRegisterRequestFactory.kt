package nz.unitracker.backend.authservice.helper.factory

import nz.unitracker.backend.authservice.web.dto.RegisterRequest

fun createTestRegisterRequest(
    firstName: String = "Jane",
    lastName: String = "Doe",
    email: String = "jane.doe@gmail.com",
    password: String = "Abc123!!",
    confirmPassword: String = password,
) = RegisterRequest(
    firstName = firstName,
    lastName = lastName,
    email = email,
    password = password,
    confirmPassword = confirmPassword,
)
