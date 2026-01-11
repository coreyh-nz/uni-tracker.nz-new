package nz.unitracker.backend.commontest

import nz.unitracker.backend.commontest.config.PostgresTestContainerConfig
import org.springframework.context.annotation.Import

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Import(PostgresTestContainerConfig::class)
@ExposedDataTest
annotation class ExposedPostgresDataTest
