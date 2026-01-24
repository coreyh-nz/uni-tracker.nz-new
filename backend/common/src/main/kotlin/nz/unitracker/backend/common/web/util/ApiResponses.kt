package nz.unitracker.backend.common.web.util

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.net.URI

object ApiResponses {
    fun ok(block: ResponseEntity.HeadersBuilder<*>.() -> Unit = {}): ResponseEntity<Unit> = custom(HttpStatus.OK, Unit, block)

    fun <T : Any> ok(
        body: T,
        block: ResponseEntity.BodyBuilder.() -> Unit = {},
    ): ResponseEntity<T> = custom(HttpStatus.OK, body, block)

    fun created(
        location: URI? = null,
        block: ResponseEntity.HeadersBuilder<*>.() -> Unit = {},
    ): ResponseEntity<Unit> = created(Unit, location, block)

    fun <T : Any> created(
        body: T,
        location: URI? = null,
        block: ResponseEntity.BodyBuilder.() -> Unit = {},
    ): ResponseEntity<T> =
        custom(HttpStatus.CREATED, body) {
            location?.let { location(it) }
            block()
        }

    fun noContent(block: ResponseEntity.HeadersBuilder<*>.() -> Unit = {}): ResponseEntity<Void> =
        ResponseEntity.noContent().apply(block).build()

    fun <T : Any> badRequest(
        body: T,
        block: ResponseEntity.BodyBuilder.() -> Unit = {},
    ): ResponseEntity<T> = custom(HttpStatus.BAD_REQUEST, body, block)

    fun <T : Any> unauthorized(
        body: T,
        block: ResponseEntity.BodyBuilder.() -> Unit = {},
    ): ResponseEntity<T> = custom(HttpStatus.UNAUTHORIZED, body, block)

    fun <T : Any> forbidden(
        body: T,
        block: ResponseEntity.BodyBuilder.() -> Unit = {},
    ): ResponseEntity<T> = custom(HttpStatus.FORBIDDEN, body, block)

    fun <T : Any> notFound(
        body: T,
        block: ResponseEntity.BodyBuilder.() -> Unit = {},
    ): ResponseEntity<T> = custom(HttpStatus.NOT_FOUND, body, block)

    fun <T : Any> internal(
        body: T,
        block: ResponseEntity.BodyBuilder.() -> Unit = {},
    ): ResponseEntity<T> = custom(HttpStatus.INTERNAL_SERVER_ERROR, body, block)

    private fun <T : Any> custom(
        status: HttpStatus,
        body: T,
        block: ResponseEntity.BodyBuilder.() -> Unit = {},
    ): ResponseEntity<T> {
        val builder = ResponseEntity.status(status)
        builder.block()
        return builder.body(body)
    }
}
