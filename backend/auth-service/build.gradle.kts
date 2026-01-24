plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
}

description = "auth-service"

dependencies {
    implementation(project(":common"))

    // Spring Boot Starters
    implementation(libs.spring.boot.starter.security.oauth2.client)

    testImplementation(project(":commontest"))
}
