package nz.unitracker.backend.authservice.domain.service

import nz.unitracker.backend.authservice.domain.model.user.credential.UserCredentialType
import nz.unitracker.backend.authservice.domain.model.user.credential.UserPasswordCredential
import nz.unitracker.backend.authservice.domain.repository.UserCredentialRepository
import nz.unitracker.backend.common.domain.model.user.UserId
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import kotlin.time.Clock

@Service
class UserCredentialService(
    private val userCredentialRepository: UserCredentialRepository,
    private val passwordEncoder: PasswordEncoder,
    private val clock: Clock = Clock.System,
) {
    fun createPassword(
        userId: UserId,
        password: String,
    ) {
        val now = clock.now()
        val hashedPassword = passwordEncoder.encode(password)
        val userCredential =
            UserPasswordCredential(
                userId = userId,
                type = UserCredentialType.PASSWORD,
                passwordHash = hashedPassword,
                lastUsedAt = null,
                createdAt = now,
                updatedAt = now,
            )
        transaction {
            userCredentialRepository.save(userCredential)
        }
    }

    fun verifyPassword(
        userId: UserId,
        password: String,
    ): Boolean =
        transaction {
            userCredentialRepository
                .findByIdWithTypePassword(userId)
                ?.let {
                    passwordEncoder.matches(password, it.passwordHash)
                } ?: false
        }
}
