package nz.unitracker.backend.authservice.web.support

object Routes {
    object V1 {
        private const val V1_PREFIX = "/v1/auth"
        const val LOGIN = "$V1_PREFIX/login"
        const val REGISTER = "$V1_PREFIX/register"
        const val REFRESH = "$V1_PREFIX/refresh"
    }
}
