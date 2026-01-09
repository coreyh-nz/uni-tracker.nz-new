package nz.unitracker.backend.common.domain.model.id

import io.github.thibaultmeyer.cuid.CUID
import nz.unitracker.backend.common.domain.model.user.UserId

fun generateId(): String = CUID.randomCUID2().toString()

fun generateUserId(): UserId = UserId(generateId())
