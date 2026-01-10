package nz.unitracker.backend.authservice.infrastructure.persistence.table

import nz.unitracker.backend.common.domain.model.user.UserRole
import org.jetbrains.exposed.v1.core.Table

object ExposedUserRoleTable : Table("user_role") {
    val userId = reference("user_id", ExposedUserTable)
    val role = enumerationByName<UserRole>("role", length = 16)

    override val primaryKey = PrimaryKey(userId, role)
}
