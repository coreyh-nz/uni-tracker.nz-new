package nz.unitracker.backend.authservice.helper.creator

import nz.unitracker.backend.authservice.domain.model.user.User
import nz.unitracker.backend.authservice.domain.service.AuthService
import nz.unitracker.backend.authservice.domain.service.UserService
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.springframework.boot.test.context.TestComponent
import org.springframework.context.annotation.Import

@TestComponent
class TestUserCreator(
    private val authService: AuthService,
    private val userService: UserService,
) {
    fun createTestUserWithPassword(
        firstName: String = "John",
        lastName: String = "Smith",
        email: String = "john@smith.com",
        password: String = "Abc123!!",
    ): User =
        transaction {
            authService.register(
                firstName = firstName,
                lastName = lastName,
                email = email,
                password = password,
            )
            requireNotNull(userService.findByEmail(email))
        }
}

@Import(TestUserCreator::class)
annotation class UseTestUserCreator
