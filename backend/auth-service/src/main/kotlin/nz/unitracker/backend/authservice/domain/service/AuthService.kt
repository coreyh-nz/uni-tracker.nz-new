package nz.unitracker.backend.authservice.domain.service

import nz.unitracker.backend.authservice.application.exception.UserEmailAlreadyExistsException
import nz.unitracker.backend.authservice.domain.model.user.User
import nz.unitracker.backend.authservice.domain.model.user.token.IssuedTokens
import nz.unitracker.backend.common.application.exception.UserUnauthenticatedException
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userService: UserService,
    private val userCredentialService: UserCredentialService,
    private val tokenService: TokenService,
) {
    fun login(
        email: String,
        password: String,
    ): IssuedTokens {
        val user =
            userService
                .findByEmail(email)
                ?.takeIf {
                    userCredentialService.verifyPassword(it.id, password)
                }
                ?: throw UserUnauthenticatedException()

        return generateTokens(user)
    }

    fun register(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
    ) = transaction {
        userService
            .findByEmail(email)
            ?.let { throw UserEmailAlreadyExistsException() }

        val user =
            userService.create(
                email = email,
                firstName = firstName,
                lastName = lastName,
            )
        userCredentialService.createPassword(user.id, password)
    }

    fun refresh(refreshToken: String): IssuedTokens =
        runCatching {
            tokenService.decodeRefreshToken(refreshToken)
        }.onFailure { exception -> exception.printStackTrace() }
            .getOrElse { throw UserUnauthenticatedException() }
            .let { issuedRefreshToken ->
                transaction {
                    userService
                        .findById(issuedRefreshToken.userId)
                        ?.let { user -> generateTokens(user) }
                        ?: throw UserUnauthenticatedException()
                }
            }

    private fun generateTokens(user: User): IssuedTokens {
        val accessToken = tokenService.generateAccessToken(user)
        val refreshToken = tokenService.generateRefreshToken(user)
        return IssuedTokens(accessToken, refreshToken)
    }
}
