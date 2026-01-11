package nz.unitracker.backend.common.infrastructure.persistence.table

import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.Op
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.core.isNull
import org.jetbrains.exposed.v1.datetime.timestamp
import kotlin.time.Instant

/**
 * Marks a table as soft-deletable with an optional `deleted_at` timestamp.
 */
interface DeletableTable {
    /** Timestamp when the entity was deleted, or null if not deleted */
    val deletedAt: Column<Instant?>
}

/**
 * Adds a nullable `deleted_at` column to a [DeletableTable].
 */
fun <T> T.deletedAt(): Column<Instant?> where T : Table, T : DeletableTable = timestamp("deleted_at").nullable()

/**
 * Returns a condition that checks if the entity is not deleted.
 */
fun <T> T.notDeleted(): Op<Boolean> where T : Table, T : DeletableTable = this.deletedAt.isNull()
