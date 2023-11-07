@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("java-library")
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
}

/*val javaVersion = JavaVersion.VERSION_11
val jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11

android.compileOptions {
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
}*/
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}