package nz.unitracker.backend.authservice.infrastructure.persistence.repository

import nz.unitracker.backend.authservice.domain.model.user.User
import nz.unitracker.backend.authservice.domain.repository.UserRepository
import nz.unitracker.backend.authservice.infrastructure.persistence.table.ExposedUserRoleTable
import nz.unitracker.backend.authservice.infrastructure.persistence.table.ExposedUserTable
import nz.unitracker.backend.common.domain.model.user.UserId
import nz.unitracker.backend.common.domain.model.user.UserRole
import nz.unitracker.backend.common.infrastructure.persistence.table.function.ArrayAggFunction
import nz.unitracker.backend.common.infrastructure.persistence.table.function.arrayAgg
import nz.unitracker.backend.common.infrastructure.persistence.table.notDeleted
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.and
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.inList
import org.jetbrains.exposed.v1.jdbc.batchInsert
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.select
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.jetbrains.exposed.v1.jdbc.upsert
import org.springframework.stereotype.Repository

@Repository
class ExposedUserRepository : UserRepository {
    override fun save(user: User) =
        transaction {
            ExposedUserTable.upsert(ExposedUserTable.id) {
                it[ExposedUserTable.id] = user.id.id
                it[ExposedUserTable.email] = user.email
                it[ExposedUserTable.firstName] = user.firstName
                it[ExposedUserTable.lastName] = user.lastName
                it[ExposedUserTable.createdAt] = user.createdAt
                it[ExposedUserTable.updatedAt] = user.updatedAt
                it[ExposedUserTable.deletedAt] = user.deletedAt
            }
            updateRoles(user.id, user.roles)
        }

    override fun findById(id: UserId): User? =
        transaction {
            val rolesAgg = ExposedUserRoleTable.role.arrayAgg()
            (ExposedUserTable leftJoin ExposedUserRoleTable)
                .select(ExposedUserTable.columns + rolesAgg)
                .where { (ExposedUserTable.id eq id.id) and ExposedUserTable.notDeleted() }
                .groupBy(ExposedUserTable.id)
                .singleOrNull()
                ?.toUser(rolesAgg)
        }

    override fun findByEmail(email: String): User? =
        transaction {
            val rolesAgg = ExposedUserRoleTable.role.arrayAgg()
            (ExposedUserTable leftJoin ExposedUserRoleTable)
                .select(ExposedUserTable.columns + rolesAgg)
                .where { (ExposedUserTable.email eq email) and ExposedUserTable.notDeleted() }
                .groupBy(ExposedUserTable.id)
                .singleOrNull()
                ?.toUser(rolesAgg)
        }

    private fun updateRoles(
        userId: UserId,
        updatedRoles: Set<UserRole>,
    ) {
        val currentRoles =
            ExposedUserRoleTable
                .select(ExposedUserRoleTable.role)
                .where { ExposedUserRoleTable.userId eq userId.id }
                .map { it[ExposedUserRoleTable.role] }
                .toSet()

        val rolesToRemove = currentRoles - updatedRoles
        val rolesToAdd = updatedRoles - currentRoles
        rolesToRemove
            .takeIf { it.isNotEmpty() }
            ?.let { roles ->
                ExposedUserRoleTable.deleteWhere {
                    (ExposedUserRoleTable.userId eq userId.id) and
                        (ExposedUserRoleTable.role inList roles)
                }
            }
        rolesToAdd
            .takeIf { it.isNotEmpty() }
            ?.let { roles ->
                ExposedUserRoleTable.batchInsert(roles) {
                    this[ExposedUserRoleTable.userId] = userId.id
                    this[ExposedUserRoleTable.role] = it
                }
            }
    }

    private fun ResultRow.toUser(rolesAgg: ArrayAggFunction<UserRole>): User =
        User(
            id = UserId(this[ExposedUserTable.id].value),
            email = this[ExposedUserTable.email],
            firstName = this[ExposedUserTable.firstName],
            lastName = this[ExposedUserTable.lastName],
            roles = this[rolesAgg].filterNotNull().toSet(),
            createdAt = this[ExposedUserTable.createdAt],
            updatedAt = this[ExposedUserTable.updatedAt],
            deletedAt = this[ExposedUserTable.deletedAt],
        )
}
