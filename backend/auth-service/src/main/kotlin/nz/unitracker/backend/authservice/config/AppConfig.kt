package nz.unitracker.backend.authservice.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import java.net.URI

@Configuration
@EnableConfigurationProperties(AppProperties::class)
class AppConfig

@ConfigurationProperties(prefix = "app")
class AppProperties(
    val frontendUrl: URI,
)
