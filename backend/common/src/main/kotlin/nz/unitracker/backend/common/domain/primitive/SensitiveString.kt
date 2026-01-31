package nz.unitracker.backend.common.domain.primitive

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * A lightweight value object representing sensitive textual data such as
 * passwords, tokens, API keys, or other secret values.
 *
 * This type behaves like a regular [String] at runtime but overrides
 * [toString] to prevent accidental exposure of the underlying value in logs,
 * error messages, or debugging output.
 */
@JvmInline
value class SensitiveString(
    @field:JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    val value: String,
) {
    override fun toString(): String = "****"

    companion object {
        val Empty = SensitiveString("")
    }
}
