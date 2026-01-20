package nz.unitracker.backend.commontest.assertions

import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.maps.shouldHaveKey
import io.kotest.matchers.nulls.shouldNotBeNull
import nz.unitracker.backend.common.application.exception.FieldValidationException
import nz.unitracker.backend.common.application.exception.FieldViolation
import nz.unitracker.backend.common.web.dto.ApiErrorResponse
import kotlin.reflect.KProperty1
import kotlin.test.fail

private enum class MatchMode {
    CONTAINS,
    ALL,
}

class FieldValidationAssertion(
    private val fields: Map<String, FieldViolation>,
) {
    private data class FieldExpectation(
        val messages: List<String>,
        val mode: MatchMode,
    )

    private val expectedFields = mutableMapOf<String, FieldExpectation>()

    infix fun <T> KProperty1<T, String>.shouldContain(message: String) = name shouldContain message

    infix fun <T> KProperty1<T, String>.shouldContainOnly(message: String) = name shouldContainOnly message

    infix fun <T> KProperty1<T, String>.shouldContainAll(messages: List<String>) = name shouldContainAll messages

    infix fun String.shouldContain(message: String) {
        require(this !in expectedFields) { "Field '$this' already has an expectation registered" }
        expectedFields[this] =
            FieldExpectation(
                messages = listOf(message),
                mode = MatchMode.CONTAINS,
            )
    }

    infix fun String.shouldContainOnly(message: String) {
        require(this !in expectedFields) { "Field '$this' already has an expectation registered" }
        expectedFields[this] =
            FieldExpectation(
                messages = listOf(message),
                mode = MatchMode.ALL,
            )
    }

    infix fun String.shouldContainAll(messages: List<String>) {
        require(this !in expectedFields) { "Field '$this' already has an expectation registered" }
        expectedFields[this] =
            FieldExpectation(
                messages = messages,
                mode = MatchMode.ALL,
            )
    }

    fun assertAll() {
        expectedFields.forEach { (field, expectation) ->
            fields shouldHaveKey field
            fields[field].shouldNotBeNull()

            val fieldViolation = fields[field]
            fieldViolation.shouldNotBeNull()

            val violations = fieldViolation.violations
            violations.shouldNotBeNull()

            expectation.messages.forEach { expected ->
                if (violations.none { it.contains(expected) }) {
                    fail(
                        "Expected violation for field '$field': '$expected' was not found.\n" +
                            "Actual violations: ${violations.joinToString(prefix = "[", postfix = "]")}",
                    )
                }
            }

            if (expectation.mode == MatchMode.ALL) {
                violations shouldHaveSize expectation.messages.size
            }
        }
    }
}

infix fun ApiErrorResponse.shouldHaveErrors(block: FieldValidationAssertion.() -> Unit) {
    fields.shouldNotBeNull()

    val assertion = FieldValidationAssertion(this.fields!!)
    assertion.block()
    assertion.assertAll()
}

infix fun FieldValidationException.shouldHaveErrors(block: FieldValidationAssertion.() -> Unit) {
    val assertion = FieldValidationAssertion(this.fields)
    assertion.block()
    assertion.assertAll()
}
