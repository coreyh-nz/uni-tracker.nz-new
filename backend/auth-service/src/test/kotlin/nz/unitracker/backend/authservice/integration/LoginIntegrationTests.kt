package nz.unitracker.backend.authservice.integration

import nz.unitracker.backend.authservice.domain.service.TokenService
import nz.unitracker.backend.authservice.helper.AuthIntegrationTest
import nz.unitracker.backend.authservice.helper.assertion.shouldHaveValidAccessToken
import nz.unitracker.backend.authservice.helper.assertion.shouldHaveValidRefreshToken
import nz.unitracker.backend.authservice.helper.creator.TestUserCreator
import nz.unitracker.backend.authservice.helper.creator.UseTestUserCreator
import nz.unitracker.backend.authservice.helper.factory.createTestLoginRequest
import nz.unitracker.backend.authservice.web.support.AuthCookies.ACCESS_TOKEN_COOKIE_NAME
import nz.unitracker.backend.authservice.web.support.AuthCookies.REFRESH_TOKEN_COOKIE_NAME
import nz.unitracker.backend.authservice.web.support.Routes
import nz.unitracker.backend.commontest.extension.andReturn
import nz.unitracker.backend.commontest.extension.toJson
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.transaction.annotation.Transactional

@AuthIntegrationTest
@Transactional
@UseTestUserCreator
class LoginIntegrationTests(
    val mockMvc: MockMvc,
    val tokenService: TokenService,
    val testUserCreator: TestUserCreator,
) {
    // blue sky
    @Test
    fun `given registered user when logging in with correct credentials then should succeed and issue auth cookies`() {
        val password = "Abc123!!"
        val user = testUserCreator.createTestUserWithPassword(password = password)
        val request =
            createTestLoginRequest(
                email = user.email,
                password = password,
            )

        mockMvc
            .post(Routes.V1.LOGIN) {
                contentType = MediaType.APPLICATION_JSON
                content = request.toJson()
            }.andExpect {
                status { isOk() }
            }.andReturn {
                response shouldHaveValidAccessToken tokenService
                response shouldHaveValidRefreshToken tokenService
            }
    }

    // exceptional
    @Test
    fun `given invalid email when logging in then authentication should fail with unauthorized`() {
        val request = createTestLoginRequest()

        mockMvc
            .post(Routes.V1.LOGIN) {
                contentType = MediaType.APPLICATION_JSON
                content = request.toJson()
            }.andExpect {
                status { isUnauthorized() }
                cookie {
                    doesNotExist(ACCESS_TOKEN_COOKIE_NAME)
                    doesNotExist(REFRESH_TOKEN_COOKIE_NAME)
                }
            }
    }

    @Test
    fun `given registered user when logging in with incorrect password then authentication should fail with unauthorized`() {
        val password = "Abc123!!"
        val incorrectPassword = "Cba321!!"
        val user = testUserCreator.createTestUserWithPassword(password = password)
        val request =
            createTestLoginRequest(
                email = user.email,
                password = incorrectPassword,
            )

        mockMvc
            .post(Routes.V1.LOGIN) {
                contentType = MediaType.APPLICATION_JSON
                content = request.toJson()
            }.andExpect {
                status { isUnauthorized() }
                cookie {
                    doesNotExist(ACCESS_TOKEN_COOKIE_NAME)
                    doesNotExist(REFRESH_TOKEN_COOKIE_NAME)
                }
            }
    }
}
