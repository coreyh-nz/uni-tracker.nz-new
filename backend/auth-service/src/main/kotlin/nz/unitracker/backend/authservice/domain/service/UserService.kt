package nz.unitracker.backend.authservice.domain.service

import nz.unitracker.backend.authservice.domain.model.user.User
import nz.unitracker.backend.authservice.domain.repository.UserRepository
import nz.unitracker.backend.common.domain.model.id.generateUserId
import nz.unitracker.backend.common.domain.model.user.UserId
import nz.unitracker.backend.common.domain.model.user.UserRole
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.springframework.stereotype.Service
import kotlin.time.Clock

@Service
class UserService(
    private val userRepository: UserRepository,
    private val clock: Clock = Clock.System,
) {
    fun create(
        email: String,
        firstName: String,
        lastName: String,
    ): User =
        transaction {
            User(
                id = generateUserId(),
                email = email,
                firstName = firstName,
                lastName = lastName,
                roles = setOf(UserRole.USER),
                createdAt = clock.now(),
                updatedAt = clock.now(),
                deletedAt = null,
            ).apply { userRepository.save(this) }
        }

    fun findById(userId: UserId): User? =
        transaction {
            userRepository.findById(userId)
        }

    fun findByEmail(email: String): User? =
        transaction {
            userRepository.findByEmail(email)
        }
}
