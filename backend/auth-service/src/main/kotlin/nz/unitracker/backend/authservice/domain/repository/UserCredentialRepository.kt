package nz.unitracker.backend.authservice.domain.repository

import nz.unitracker.backend.authservice.domain.model.user.credential.UserCredential
import nz.unitracker.backend.authservice.domain.model.user.credential.UserPasswordCredential
import nz.unitracker.backend.common.domain.model.user.UserId

interface UserCredentialRepository {
    fun save(userCredential: UserCredential)

    fun findById(id: UserId): List<UserCredential>

    fun findByIdWithTypePassword(id: UserId): UserPasswordCredential?
}
