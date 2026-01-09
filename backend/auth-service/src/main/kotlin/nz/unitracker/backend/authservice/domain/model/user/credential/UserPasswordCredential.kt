package nz.unitracker.backend.authservice.domain.model.user.credential

import nz.unitracker.backend.common.domain.model.user.UserId
import kotlin.time.Instant

data class UserPasswordCredential(
    override val userId: UserId,
    override val type: UserCredentialType,
    val passwordHash: String,
    override val lastUsedAt: Instant?,
    override val createdAt: Instant,
    override val updatedAt: Instant,
) : UserCredential
