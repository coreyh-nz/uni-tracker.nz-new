plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
}

description = "auth-service"

dependencies {
    implementation(project(":common"))

    testImplementation(project(":commontest"))
}