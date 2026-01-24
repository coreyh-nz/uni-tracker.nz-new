package nz.unitracker.backend.authservice.config

import nz.unitracker.backend.authservice.web.support.Routes
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            sessionManagement { sessionCreationPolicy = SessionCreationPolicy.STATELESS }
            formLogin { disable() }
            csrf { disable() }

            authorizeHttpRequests {
                authorize(Routes.V1.LOGIN, permitAll)
                authorize(Routes.V1.REGISTER, permitAll)
                authorize(Routes.V1.REFRESH, authenticated)
                authorize(anyRequest, authenticated)
            }
        }
        return http.build()
    }
}
