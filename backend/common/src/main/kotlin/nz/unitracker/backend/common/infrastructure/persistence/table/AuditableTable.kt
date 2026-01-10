package nz.unitracker.backend.common.infrastructure.persistence.table

import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.datetime.timestamp
import kotlin.time.Instant

/**
 * Marks a table as auditable with timestamps for creation and updates.
 */
interface AuditableTable {
    /** Timestamp when the entity was created */
    val createdAt: Column<Instant>

    /** Timestamp when the entity was last updated */
    val updatedAt: Column<Instant>
}

/**
 * Adds a `created_at` column to an [AuditableTable].
 */
fun <T> T.createdAt(): Column<Instant> where T : Table, T : AuditableTable = timestamp("created_at")

/**
 * Adds an `updated_at` column to an [AuditableTable].
 */
fun <T> T.updatedAt(): Column<Instant> where T : Table, T : AuditableTable = timestamp("updated_at")
