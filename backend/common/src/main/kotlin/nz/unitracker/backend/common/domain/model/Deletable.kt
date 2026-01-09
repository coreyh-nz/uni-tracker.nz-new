package nz.unitracker.backend.common.domain.model

import kotlin.time.Instant

/**
 * Represents an entity that is soft-deletable
 */
interface Deletable {
    val deletedAt: Instant?
}
