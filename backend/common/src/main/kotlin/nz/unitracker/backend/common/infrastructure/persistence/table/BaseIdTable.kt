package nz.unitracker.backend.common.infrastructure.persistence.table

import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IdTable

/**
 * Base table with a string ID for all entities.
 *
 * The table name is prefixed with `ut_`.
 */
abstract class BaseIdTable(
    name: String,
) : IdTable<String>("ut_$name") {
    /** Primary key column */
    override val id: Column<EntityID<String>> = varchar("id", 24).entityId()
}
