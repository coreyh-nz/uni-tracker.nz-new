package nz.unitracker.backend.common.application.exception

class UserNotFoundException
    private constructor(
        message: String = "User not found",
    ) : EntityNotFoundException(message) {
        companion object {
            fun byId(id: String) = UserNotFoundException("User with id [$id] not found")
        }
    }
