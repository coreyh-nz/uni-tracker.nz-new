package nz.unitracker.backend.authservice.domain.model.user.token

import nz.unitracker.backend.common.domain.model.id.JwtId
import nz.unitracker.backend.common.domain.model.user.UserId
import nz.unitracker.backend.common.domain.primitive.SensitiveString
import kotlin.time.Duration
import kotlin.time.Instant

data class IssuedToken(
    val id: JwtId,
    val userId: UserId,
    val value: SensitiveString,
    val lifetime: Duration,
    val issuedAt: Instant,
    val expiresAt: Instant,
)
