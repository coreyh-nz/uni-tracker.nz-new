package nz.unitracker.backend.authservice.domain.repository

import nz.unitracker.backend.authservice.domain.model.user.User
import nz.unitracker.backend.common.domain.model.user.UserId

interface UserRepository {
    fun save(user: User)

    fun findById(id: UserId): User?

    fun findByEmail(email: String): User?
}
