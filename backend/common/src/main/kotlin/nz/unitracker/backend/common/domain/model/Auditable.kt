package nz.unitracker.backend.common.domain.model

import kotlin.time.Instant

/**
 * Represents an entity with audit timestamps.
 */
interface Auditable {
    val createdAt: Instant
    val updatedAt: Instant
}
