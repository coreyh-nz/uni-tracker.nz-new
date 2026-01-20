package nz.unitracker.backend.authservice.helper.assertion

import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.nulls.shouldNotBeNull
import nz.unitracker.backend.authservice.domain.service.TokenService
import nz.unitracker.backend.authservice.web.support.AuthCookies
import org.springframework.mock.web.MockHttpServletResponse

infix fun MockHttpServletResponse.shouldHaveValidAccessToken(tokenService: TokenService) {
    val cookie = getCookie(AuthCookies.ACCESS_TOKEN_COOKIE_NAME).shouldNotBeNull()
    cookie.apply {
        isHttpOnly.shouldBeTrue()
        secure.shouldBeTrue()
        tokenService.decodeToken(value)
    }
}

infix fun MockHttpServletResponse.shouldHaveValidRefreshToken(tokenService: TokenService) {
    val cookie = getCookie(AuthCookies.REFRESH_TOKEN_COOKIE_NAME).shouldNotBeNull()
    cookie.apply {
        isHttpOnly.shouldBeTrue()
        secure.shouldBeTrue()
        tokenService.decodeToken(value)
    }
}
