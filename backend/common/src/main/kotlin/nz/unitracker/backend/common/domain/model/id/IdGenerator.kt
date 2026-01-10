package nz.unitracker.backend.common.domain.model.id

import io.github.thibaultmeyer.cuid.CUID
import nz.unitracker.backend.common.domain.model.user.UserId
import nz.unitracker.common.domain.model.id.JwtId

fun generateId(): String = CUID.randomCUID2().toString()

fun generateJwtId(): JwtId = JwtId(generateId())

fun generateUserId(): UserId = UserId(generateId())
