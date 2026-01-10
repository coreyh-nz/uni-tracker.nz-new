package nz.unitracker.backend.authservice.domain.model.user.token

import nz.unitracker.backend.common.domain.model.id.JwtId
import nz.unitracker.backend.common.domain.model.user.UserId
import kotlin.time.Duration
import kotlin.time.Instant

data class IssuedToken(
    val id: JwtId,
    val userId: UserId,
    val value: String,
    val lifetime: Duration,
    val issuedAt: Instant,
    val expiresAt: Instant,
)
