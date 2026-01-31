package nz.unitracker.backend.authservice.web.controller

import nz.unitracker.backend.authservice.domain.service.AuthService
import nz.unitracker.backend.authservice.web.dto.LoginRequest
import nz.unitracker.backend.authservice.web.dto.RegisterRequest
import nz.unitracker.backend.authservice.web.support.AuthCookies
import nz.unitracker.backend.authservice.web.support.AuthCookies.addAuthTokens
import nz.unitracker.backend.authservice.web.support.Routes
import nz.unitracker.backend.authservice.web.validation.validate
import nz.unitracker.backend.common.application.exception.UserUnauthenticatedException
import nz.unitracker.backend.common.web.util.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthRestController(
    private val authService: AuthService,
) {
    @PostMapping(Routes.V1.REGISTER)
    fun register(
        @RequestBody request: RegisterRequest,
    ): ResponseEntity<Unit> {
        val request = request.validate()
        authService.register(
            firstName = request.firstName,
            lastName = request.lastName,
            email = request.email,
            password = request.password.value,
        )
        return ApiResponses.created()
    }

    @PostMapping(Routes.V1.LOGIN)
    fun login(
        @RequestBody request: LoginRequest,
    ): ResponseEntity<Unit> {
        val issuedAuthTokens =
            authService.login(
                email = request.email,
                password = request.password.value,
            )
        return ApiResponses.ok {
            addAuthTokens(issuedAuthTokens)
        }
    }

    @PostMapping(Routes.V1.REFRESH)
    fun refresh(
        @CookieValue(name = AuthCookies.REFRESH_TOKEN_COOKIE_NAME) refreshToken: String?,
    ): ResponseEntity<Unit> {
        if (refreshToken == null) throw UserUnauthenticatedException()

        val issuedAuthTokens = authService.refresh(refreshToken)
        return ApiResponses.ok {
            addAuthTokens(issuedAuthTokens)
        }
    }
}
