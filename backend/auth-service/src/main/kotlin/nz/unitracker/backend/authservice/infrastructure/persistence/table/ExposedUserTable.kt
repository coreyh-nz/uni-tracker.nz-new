package nz.unitracker.backend.authservice.infrastructure.persistence.table

import nz.unitracker.backend.common.infrastructure.persistence.table.AuditableTable
import nz.unitracker.backend.common.infrastructure.persistence.table.BaseIdTable
import nz.unitracker.backend.common.infrastructure.persistence.table.DeletableTable
import nz.unitracker.backend.common.infrastructure.persistence.table.createdAt
import nz.unitracker.backend.common.infrastructure.persistence.table.deletedAt
import nz.unitracker.backend.common.infrastructure.persistence.table.updatedAt

object ExposedUserTable : BaseIdTable("user"), AuditableTable, DeletableTable {
    val email = varchar("email", 255).uniqueIndex()
    val firstName = varchar("first_name", 100)
    val lastName = varchar("last_name", 100)

    override val createdAt = createdAt()
    override val updatedAt = updatedAt()
    override val deletedAt = deletedAt()
}
