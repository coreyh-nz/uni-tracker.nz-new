import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.spring) apply false
    alias(libs.plugins.spring.boot) apply false
    alias(libs.plugins.spring.dependency.management)
}

subprojects {
    group = "nz.unitracker.backend"
    version = "0.0.1-SNAPSHOT"

    plugins.withId("java") {
        configure<JavaPluginExtension> {
            toolchain {
                languageVersion.set(JavaLanguageVersion.of(25))
            }
        }
    }

    plugins.withId("org.jetbrains.kotlin.jvm") {
        configure<KotlinJvmProjectExtension> {
            compilerOptions {
                freeCompilerArgs.addAll("-Xjsr305=strict")
            }
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    repositories {
        mavenCentral()
    }
}
