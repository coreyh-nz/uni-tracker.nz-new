plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
}

description = "common-test"

repositories {
    mavenCentral()
}

dependencies {
    api(project(":common"))

    // Testing
    api(platform(libs.testcontainers.bom))
    api(libs.testcontainers.junit)
    api(libs.testcontainers.postgres)

    api(libs.spring.boot.starter.test)
    api(libs.kotlin.test.junit5)
    api(libs.kotest.assertions)
    api(libs.kotest.runner)
    api(libs.kotest.engine)
    api(libs.mockk)
    runtimeOnly(libs.junit.platform.launcher)
}

tasks.test {
    useJUnitPlatform()
}
