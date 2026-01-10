package nz.unitracker.backend.authservice.domain.service

import nz.unitracker.backend.authservice.config.JwtProperties
import nz.unitracker.backend.authservice.domain.model.user.User
import nz.unitracker.backend.authservice.domain.model.user.token.IssuedToken
import nz.unitracker.backend.common.domain.model.id.JwtId
import nz.unitracker.backend.common.domain.model.id.generateJwtId
import nz.unitracker.backend.common.domain.model.user.UserId
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.security.oauth2.jwt.JwtException
import org.springframework.stereotype.Service
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.toJavaInstant
import kotlin.time.toKotlinInstant

@Service
class TokenService(
    private val jwtProperties: JwtProperties,
    private val jwtEncoder: JwtEncoder,
    private val jwtDecoder: JwtDecoder,
    private val clock: Clock = Clock.System,
) {
    fun generateAccessToken(user: User): IssuedToken {
        val lifetime = jwtProperties.accessLifetime
        return generateToken(user.id, lifetime) {
            claim("scope", user.roles.map { it.name })
        }
    }

    fun generateRefreshToken(user: User): IssuedToken {
        val lifetime = jwtProperties.refreshLifetime
        return generateToken(user.id, lifetime)
    }

    fun decodeToken(token: String): IssuedToken {
        val jwt = jwtDecoder.decode(token)
        val issuedAt =
            jwt.issuedAt?.toKotlinInstant()
                ?: throw JwtException("Missing issuedAt claim")
        val expiresAt =
            jwt.expiresAt?.toKotlinInstant()
                ?: throw JwtException("Missing expiresAt claim")
        return IssuedToken(
            id = JwtId(jwt.id),
            userId = UserId(jwt.subject),
            value = jwt.tokenValue,
            lifetime = expiresAt - issuedAt,
            issuedAt = issuedAt,
            expiresAt = expiresAt,
        )
    }

    private fun generateToken(
        userId: UserId,
        lifetime: Duration,
        block: (JwtClaimsSet.Builder.() -> Unit)? = null,
    ): IssuedToken {
        val jwtId = generateJwtId()
        val now = clock.now()
        val expiresAt = now + lifetime
        val claims =
            JwtClaimsSet
                .builder()
                .issuer(jwtProperties.issuer)
                .audience(listOf(jwtProperties.audience))
                .subject(userId.id)
                .issuedAt(now.toJavaInstant())
                .expiresAt(expiresAt.toJavaInstant())
                .id(jwtId.id)
                .apply { block?.invoke(this) }
                .build()
        val jwt = jwtEncoder.encode(JwtEncoderParameters.from(claims))
        return IssuedToken(
            id = jwtId,
            userId = userId,
            value = jwt.tokenValue,
            lifetime = lifetime,
            issuedAt = now,
            expiresAt = expiresAt,
        )
    }
}
