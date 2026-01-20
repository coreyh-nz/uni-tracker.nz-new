package nz.unitracker.backend.authservice.integration

import io.kotest.matchers.nulls.shouldNotBeNull
import nz.unitracker.backend.authservice.application.validation.UserValidationMessages
import nz.unitracker.backend.authservice.domain.service.UserCredentialService
import nz.unitracker.backend.authservice.domain.service.UserService
import nz.unitracker.backend.authservice.helper.AuthIntegrationTest
import nz.unitracker.backend.authservice.helper.creator.TestUserCreator
import nz.unitracker.backend.authservice.helper.creator.UseTestUserCreator
import nz.unitracker.backend.authservice.helper.factory.createTestRegisterRequest
import nz.unitracker.backend.authservice.web.dto.RegisterRequest
import nz.unitracker.backend.authservice.web.support.Routes
import nz.unitracker.backend.commontest.assertions.shouldHaveErrors
import nz.unitracker.backend.commontest.extension.andExpectErrorResponse
import nz.unitracker.backend.commontest.extension.toJson
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.transaction.annotation.Transactional

@AuthIntegrationTest
@Transactional
@UseTestUserCreator
class RegisterIntegrationTests(
    private val mockMvc: MockMvc,
    private val userService: UserService,
    private val userCredentialService: UserCredentialService,
    private val testUserFactory: TestUserCreator,
) {
    // blue sky
    @Test
    fun `given valid registration details when registering a new user then should create user and password credentials successfully`() {
        val request = createTestRegisterRequest()

        mockMvc
            .post(Routes.V1.REGISTER) {
                contentType = MediaType.APPLICATION_JSON
                content = request.toJson()
            }.andExpect {
                status { isCreated() }
            }

        // check user was created
        val user = userService.findByEmail(request.email)
        user.shouldNotBeNull()

        // check user credentials were created and valid
        userCredentialService.verifyPassword(user.id, request.password.value)
    }

    // exceptional
    @Test
    fun `given email already registered when registering a new user then should fail with validation error`() {
        val existingUser = testUserFactory.createTestUserWithPassword()
        val request = createTestRegisterRequest(email = existingUser.email)

        mockMvc
            .post(Routes.V1.REGISTER) {
                contentType = MediaType.APPLICATION_JSON
                content = request.toJson()
            }.andExpect {
                status { isBadRequest() }
            }.andExpectErrorResponse {
                shouldHaveErrors {
                    RegisterRequest::email shouldContainOnly UserValidationMessages.EMAIL_ALREADY_IN_USE
                }
            }
    }
}
