package nz.unitracker.backend.authservice.infrastructure.persistence.repository

import io.github.oshai.kotlinlogging.KotlinLogging
import nz.unitracker.backend.authservice.domain.model.user.credential.UserCredential
import nz.unitracker.backend.authservice.domain.model.user.credential.UserCredentialType
import nz.unitracker.backend.authservice.domain.model.user.credential.UserPasswordCredential
import nz.unitracker.backend.authservice.domain.repository.UserCredentialRepository
import nz.unitracker.backend.authservice.infrastructure.persistence.table.ExposedUserCredentialTable
import nz.unitracker.backend.common.domain.model.user.UserId
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.jetbrains.exposed.v1.jdbc.upsert
import org.springframework.stereotype.Repository

@Repository
class ExposedUserCredentialRepository : UserCredentialRepository {
    private val kLogger = KotlinLogging.logger {}

    override fun save(userCredential: UserCredential) {
        transaction {
            ExposedUserCredentialTable.upsert(ExposedUserCredentialTable.userId, ExposedUserCredentialTable.type) {
                it[userId] = userCredential.userId.id
                it[type] = userCredential.type
                it[lastUsedAt] = userCredential.lastUsedAt
                it[createdAt] = userCredential.createdAt
                it[updatedAt] = userCredential.updatedAt

                // prepare for future oauth2 implementation
                when (userCredential) {
                    is UserPasswordCredential -> {
                        it[passwordHash] = userCredential.passwordHash
                        it[passwordUpdatedAt] = userCredential.updatedAt
                    }
                }
            }
        }
    }

    override fun findById(id: UserId): List<UserCredential> =
        ExposedUserCredentialTable
            .selectAll()
            .where { ExposedUserCredentialTable.userId eq id.id }
            .mapNotNull { it.toModel() }

    override fun findByIdWithTypePassword(id: UserId): UserPasswordCredential? =
        ExposedUserCredentialTable
            .selectAll()
            .where { ExposedUserCredentialTable.userId eq id.id }
            .singleOrNull()
            ?.toPasswordModel()

    private fun ResultRow.toModel(): UserCredential? =
        // prepare for future oauth2 implementation
        when (this[ExposedUserCredentialTable.type]) {
            UserCredentialType.PASSWORD -> toPasswordModel()
        }

    private fun ResultRow.toPasswordModel(): UserPasswordCredential? {
        require(this[ExposedUserCredentialTable.type] == UserCredentialType.PASSWORD)

        val userId = UserId(this[ExposedUserCredentialTable.userId].value)
        val passwordHash = this[ExposedUserCredentialTable.passwordHash]
        if (passwordHash == null) {
            kLogger.warn { "Password hash is null for user [$userId]" }
            return null
        }

        return UserPasswordCredential(
            userId = userId,
            type = this[ExposedUserCredentialTable.type],
            passwordHash = passwordHash,
            lastUsedAt = this[ExposedUserCredentialTable.lastUsedAt],
            createdAt = this[ExposedUserCredentialTable.createdAt],
            updatedAt = this[ExposedUserCredentialTable.updatedAt],
        )
    }
}
