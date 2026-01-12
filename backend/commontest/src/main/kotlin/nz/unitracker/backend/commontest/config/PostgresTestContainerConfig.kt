package nz.unitracker.backend.commontest.config

import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName
import javax.sql.DataSource

@TestConfiguration
class PostgresTestContainerConfig {
    companion object {
        val postgres: PostgreSQLContainer<*> by lazy {
            PostgreSQLContainer(DockerImageName.parse("postgres:18.0")).apply {
                withDatabaseName("testdb")
                withUsername("test")
                withPassword("test")
                start()
            }
        }
    }

    @Bean
    fun dataSource(): DataSource =
        DataSourceBuilder
            .create()
            .url(postgres.jdbcUrl)
            .username(postgres.username)
            .password(postgres.password)
            .driverClassName(postgres.driverClassName)
            .build()
}
