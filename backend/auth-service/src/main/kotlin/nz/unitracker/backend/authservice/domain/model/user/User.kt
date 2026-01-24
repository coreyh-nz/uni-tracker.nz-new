package nz.unitracker.backend.authservice.domain.model.user

import nz.unitracker.backend.common.domain.model.Auditable
import nz.unitracker.backend.common.domain.model.Deletable
import nz.unitracker.backend.common.domain.model.user.UserId
import nz.unitracker.backend.common.domain.model.user.UserRole
import kotlin.time.Instant

data class User(
    val id: UserId,
    val email: String,
    val firstName: String,
    val lastName: String,
    val roles: Set<UserRole> = setOf(),
    override val createdAt: Instant,
    override val updatedAt: Instant,
    override val deletedAt: Instant?,
) : Auditable,
    Deletable
