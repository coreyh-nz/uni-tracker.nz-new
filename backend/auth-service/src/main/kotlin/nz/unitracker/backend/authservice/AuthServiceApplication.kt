package nz.unitracker.backend.authservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import
import nz.unitracker.backend.common.web.advice.GlobalExceptionHandler as CommonGlobalExceptionHandler

@SpringBootApplication
@Import(value = [CommonGlobalExceptionHandler::class])
class AuthServiceApplication

fun main(args: Array<String>) {
    runApplication<AuthServiceApplication>(*args)
}
