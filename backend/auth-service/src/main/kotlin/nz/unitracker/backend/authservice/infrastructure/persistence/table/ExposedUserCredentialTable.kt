package nz.unitracker.backend.authservice.infrastructure.persistence.table

import nz.unitracker.backend.authservice.domain.model.user.credential.UserCredentialType
import nz.unitracker.backend.common.infrastructure.persistence.table.AuditableTable
import nz.unitracker.backend.common.infrastructure.persistence.table.createdAt
import nz.unitracker.backend.common.infrastructure.persistence.table.updatedAt
import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.datetime.timestamp
import kotlin.time.Instant

object ExposedUserCredentialTable : Table("user_credential"), AuditableTable {
    val userId = reference("user_id", ExposedUserTable)
    val type =
        enumerationByName(
            "type",
            length = 20,
            klass = UserCredentialType::class,
        )

    // password fields (nullable for future oauth implementation)
    val passwordHash = varchar("password_hash", 255).nullable()
    val passwordUpdatedAt = timestamp("password_updated_at").nullable()
    val lastUsedAt = timestamp("last_used_at").nullable()

    override val createdAt: Column<Instant> = createdAt()
    override val updatedAt: Column<Instant> = updatedAt()

    init {
        uniqueIndex(userId, type)
    }
}
