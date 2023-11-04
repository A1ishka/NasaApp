@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.serialization)
    alias(libs.plugins.ksp)
    //id("io.ktor.plugin") version "2.3.4"
}
ksp {
    arg("room.schemaLocation", "$projectDir/schema")
    arg("room.incremental", "true")
    arg("room.expandProjection", "true")
}
android {
    namespace = "by.bsuir.makogon.alina"
    compileSdk = 34

    defaultConfig {
        applicationId = "by.bsuir.makogon.alina"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += setOf(
                "DebugProbesKt.bin",
                "/META-INF/{AL2.0,LGPL2.1}",
                //"/META-INF/versions/9/previous-compilation-data.bin"
            )
        }
    }
    applicationVariants.all {
        sourceSets {
            getByName(name) {
                kotlin.srcDir("build/generated/ksp/$name/kotlin")
            }

        }
    }
}
dependencies {
    val room_version = "2.6.0"
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")

    // To use Kotlin annotation processing tool (kapt)
    //kapt("androidx.room:room-compiler:$room_version")
    // To use Kotlin Symbol Processing (KSP)
    ksp("androidx.room:room-compiler:$room_version")
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$room_version")

    // optional - Guava support for Room, including Optional and ListenableFuture
    //implementation("androidx.room:room-guava:$room_version")
    // optional - Test helpers
    testImplementation("androidx.room:room-testing:$room_version")
    // optional - Paging 3 Integration
    //implementation("androidx.room:room-paging:$room_version")

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation("androidx.compose.material3:material3:1.1.2")
    implementation("androidx.compose.material3:material3-window-size-class:1.1.2")
    implementation(libs.play.services.mlkit.text.recognition.common)
    implementation(libs.firebase.inappmessaging.ktx)
    implementation(libs.places)
    implementation("androidx.core:core-ktx:+")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
    implementation(libs.accompanist.permissions)
    implementation("androidx.navigation:navigation-runtime-ktx:[2.6.0-alpha04]")
    implementation("androidx.compose.material:material:1.5.3")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.0-rc01")
    //implementation ("com.google.accompanist:accompanist-navigation-animation: 2.7.0-alpha01")
    implementation(libs.accompanist.navigation.material)
    implementation(libs.accompanist.navigation.animation)
    implementation("com.google.accompanist:accompanist-navigation-animation:0.33.2-alpha")
    implementation("io.github.raamcosta.compose-destinations:core:1.9.50")
    implementation(libs.accompanist.drawablepainter)
    implementation(libs.accompanist.adaptive)
    implementation(libs.accompanist.testharness)
    implementation(libs.work.runtime.ktx)
    implementation(libs.collection.ktx)
    implementation(libs.coil)
    implementation(libs.coil.compose)
    implementation(libs.foundation)
    implementation(libs.animation)
    implementation(libs.ui.util)
    implementation(libs.paging.compose)
    implementation(libs.paging.runtime.ktx)
    implementation(libs.paging.common.ktx)
    implementation(libs.constraintlayout.compose)
    implementation(libs.runtime)
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.lifecycle.service)
    implementation(libs.lifecycle.process)
    implementation(libs.datastore.preferences)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.symbol.processing.api)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.koin.android)
    implementation(libs.koin.compose)
    implementation(libs.koin.core)
    implementation(libs.room.runtime)
    var ktorVersion = "2.3.5"
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    //implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-server-host-common:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    testImplementation("io.ktor:ktor-server-tests:$ktorVersion")
    //implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
    implementation("io.ktor:ktor-client-android:$ktorVersion")
    implementation("io.ktor:ktor-client-serialization:$ktorVersion")
    implementation("io.ktor:ktor-client-logging:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:1.2.3")

    implementation("androidx.compose.runtime:runtime-livedata:1.5.2")
    annotationProcessor(libs.room.compiler)

    implementation (project(":domain"))
    implementation (project(":data"))
}