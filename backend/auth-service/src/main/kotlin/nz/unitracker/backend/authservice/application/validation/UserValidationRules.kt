package nz.unitracker.backend.authservice.application.validation

import io.konform.validation.Constraint
import io.konform.validation.ValidationBuilder
import io.konform.validation.constraints.maxLength
import io.konform.validation.constraints.minLength
import io.konform.validation.constraints.pattern
import nz.unitracker.backend.common.application.validation.validateAs
import nz.unitracker.backend.common.domain.primitive.SensitiveString
import kotlin.reflect.KProperty1

object UserValidationRules {
    const val NAME_MIN_LENGTH = 2
    const val NAME_MAX_LENGTH = 32
    const val EMAIL_MAX_LENGTH = 255
    const val PASSWORD_MIN_LENGTH = 8
    const val PASSWORD_MAX_LENGTH = 128

    object Messages {
        const val FIRST_NAME_LENGTH = "First name must be between $NAME_MIN_LENGTH and $NAME_MAX_LENGTH characters"
        const val LAST_NAME_LENGTH = "Last name must be between $NAME_MIN_LENGTH and $NAME_MAX_LENGTH characters"
        const val EMAIL_REQUIRED = "Email must not be blank"
        const val EMAIL_FORMAT = "Email must be valid"
        const val EMAIL_LENGTH = "Email must not exceed $EMAIL_MAX_LENGTH characters"
        const val PASSWORD_LENGTH = "Password must be between $PASSWORD_MIN_LENGTH and $PASSWORD_MAX_LENGTH characters"
        const val PASSWORD_UPPER = "Password must contain at least one uppercase letter"
        const val PASSWORD_LOWER = "Password must contain at least one lowercase letter"
        const val PASSWORD_DIGIT = "Password must contain at least one digit"
        const val PASSWORD_SPECIAL = "Password must contain at least one special character"
    }

    fun ValidationBuilder<String>.firstNameRules(): Constraint<String> =
        run {
            minLength(NAME_MIN_LENGTH) hint Messages.FIRST_NAME_LENGTH
            maxLength(NAME_MAX_LENGTH) hint Messages.FIRST_NAME_LENGTH
        }

    fun ValidationBuilder<String>.lastNameRules(): Constraint<String> =
        run {
            minLength(NAME_MIN_LENGTH) hint Messages.LAST_NAME_LENGTH
            maxLength(NAME_MAX_LENGTH) hint Messages.LAST_NAME_LENGTH
        }

    fun ValidationBuilder<String>.emailRules(): Constraint<String> =
        run {
            minLength(1) hint Messages.EMAIL_REQUIRED
            maxLength(EMAIL_MAX_LENGTH) hint Messages.EMAIL_LENGTH
            pattern(
                Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+"),
            ) hint Messages.EMAIL_FORMAT
        }

    fun <P> ValidationBuilder<SensitiveString>.passwordRules(property: KProperty1<P, SensitiveString>): Unit =
        validateAs(
            property,
            { it.value },
        ) {
            minLength(PASSWORD_MIN_LENGTH) hint Messages.PASSWORD_LENGTH
            maxLength(PASSWORD_MAX_LENGTH) hint Messages.PASSWORD_LENGTH
            pattern(Regex(".*[A-Z].*")) hint Messages.PASSWORD_UPPER
            pattern(Regex(".*[a-z].*")) hint Messages.PASSWORD_LOWER
            pattern(Regex(".*[0-9].*")) hint Messages.PASSWORD_DIGIT
            pattern(Regex(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"|,.<>/?].*")) hint Messages.PASSWORD_SPECIAL
        }
}
