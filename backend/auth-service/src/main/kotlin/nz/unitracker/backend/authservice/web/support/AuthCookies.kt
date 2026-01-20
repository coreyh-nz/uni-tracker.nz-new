package nz.unitracker.backend.authservice.web.support

import nz.unitracker.backend.authservice.domain.model.user.token.IssuedToken
import nz.unitracker.backend.authservice.domain.model.user.token.IssuedTokens
import nz.unitracker.backend.common.web.util.CookieBuilder
import nz.unitracker.backend.common.web.util.addCookie
import org.springframework.boot.web.server.Cookie
import org.springframework.http.ResponseEntity

object AuthCookies {
    const val REFRESH_TOKEN_COOKIE_NAME = "ut_rt"
    const val ACCESS_TOKEN_COOKIE_NAME = "ut_at"

    fun ResponseEntity.HeadersBuilder<*>.addAuthTokens(issuedAuthTokens: IssuedTokens) {
        val (issuedAccessToken, issuedRefreshToken) = issuedAuthTokens
        addAccessToken(issuedAccessToken)
        addRefreshToken(issuedRefreshToken)
    }

    fun ResponseEntity.HeadersBuilder<*>.addAccessToken(issuedAccessToken: IssuedToken) {
        addAuthCookie(
            name = ACCESS_TOKEN_COOKIE_NAME,
            issuedToken = issuedAccessToken,
        )
    }

    fun ResponseEntity.HeadersBuilder<*>.addRefreshToken(issuedRefreshToken: IssuedToken) {
        addAuthCookie(
            name = REFRESH_TOKEN_COOKIE_NAME,
            issuedToken = issuedRefreshToken,
        )
    }

    private fun ResponseEntity.HeadersBuilder<*>.addAuthCookie(
        name: String,
        issuedToken: IssuedToken,
        block: (CookieBuilder.() -> Unit)? = null,
    ) {
        addCookie(name, issuedToken.value) {
            lifetime = issuedToken.lifetime
            sameSite = Cookie.SameSite.STRICT
            httpOnly = true
            secure = true
            block?.invoke(this)
        }
    }
}
