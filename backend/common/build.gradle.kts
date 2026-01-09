plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
}

description = "common"

repositories {
    mavenCentral()
}

dependencies {
    // Spring Boot Starters
    api(libs.spring.boot.starter.web)
    api(libs.spring.boot.starter.security)

    // Kotlin / Jackson
    api(libs.jackson.kotlin)
    api(libs.kotlin.reflect)

    // Utils
    api(libs.kotlin.logging)
    api(libs.cuid)
    api(libs.konform)

    // Database
    api(libs.exposed.core)
    api(libs.exposed.jdbc)
    api(libs.exposed.kotlin.datetime)
    api(libs.exposed.spring.boot.starter)
    api(libs.postgres.driver)

    // Testing
    testImplementation(libs.kotlin.test.junit5)
    testImplementation(libs.kotest.assertions)
    testImplementation(libs.mockk)
    testRuntimeOnly(libs.junit.platform.launcher)
}