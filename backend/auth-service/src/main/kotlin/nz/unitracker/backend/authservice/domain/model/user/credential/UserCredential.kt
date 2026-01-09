package nz.unitracker.backend.authservice.domain.model.user.credential

import nz.unitracker.backend.common.domain.model.Auditable
import nz.unitracker.backend.common.domain.model.user.UserId
import kotlin.time.Instant

sealed interface UserCredential : Auditable {
    val userId: UserId
    val type: UserCredentialType
    val lastUsedAt: Instant?
}
