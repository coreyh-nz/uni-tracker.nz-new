package nz.unitracker.backend.authservice.domain.model.user.token

data class IssuedTokens(
    val accessToken: IssuedToken,
    val refreshToken: IssuedToken,
)
