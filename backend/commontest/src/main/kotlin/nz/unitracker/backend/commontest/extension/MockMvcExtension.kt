package nz.unitracker.backend.commontest.extension

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import nz.unitracker.backend.common.web.dto.ApiErrorResponse
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.ResultActionsDsl

private val objectMapper = ObjectMapper().registerKotlinModule()

fun Any.toJson(): String = objectMapper.writeValueAsString(this)

fun ResultActionsDsl.andReturn(block: MvcResult.() -> Unit): MvcResult = andReturn().apply { block() }

fun ResultActionsDsl.andExpectErrorResponse(block: ApiErrorResponse.() -> Unit) {
    andReturn().apply {
        require(response.status in 400..599) {
            "Expected error response (4xx or 5xx) but got ${response.status}"
        }
        val content = response.contentAsString
        require(content.isNotBlank()) {
            "Response content is empty"
        }

        val errorResponse: ApiErrorResponse =
            try {
                objectMapper.readValue(content)
            } catch (e: Exception) {
                throw AssertionError("Failed to parse error response: ${e.message}\nContent: $content", e)
            }
        block(errorResponse)
    }
}
