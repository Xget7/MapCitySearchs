import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.kapt)
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id ("com.google.dagger.hilt.android")
    id("androidx.room")

}

android {
    namespace = "dev.xget.ualachallenge"
    compileSdk = 35

    defaultConfig {
        applicationId = "dev.xget.ualachallenge"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunner = "dev.xget.ualachallenge.hilt.CustomTestRunner"




        val properties = Properties()
        file("../local.properties").inputStream().use {
            properties.load(it)
        }
        val apiKey = properties.getProperty("MAPS_API_KEY")
        manifestPlaceholders["MAPS_API_KEY"] = apiKey
    }

    buildTypes {
        android.buildFeatures.buildConfig = true

        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    kapt {
        correctErrorTypes = true
    }
    room {
        schemaDirectory("$projectDir/schemas")
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    //Testing
    // For Mockito
    testImplementation (libs.mockito.core)
    testImplementation (libs.mockito.inline) // For mocking final classes and methods

    // For Mocking Room and Paging (if using Room and Paging in the repository)
    testImplementation (libs.androidx.room.testing)
    testImplementation (libs.androidx.paging.common.ktx)
    testImplementation(libs.test.androidx.paging)

    // For Kotlin Coroutine testing
    testImplementation(libs.kotlinx.coroutines.test)

    // For Hilt
    androidTestImplementation(libs.hilt.android.testing.v2511)
    androidTestImplementation(project(":app"))
    kaptTest(libs.hilt.android.compiler.v2511)
    kaptAndroidTest(libs.hilt.android.compiler.v2511)


    // For Truth (assertions)
    testImplementation (libs.truth)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Networking (Retrofit, Gson, Logging)
    implementation(libs.retrofit) // Retrofit for API calls
    implementation(libs.converter.gson) // Gson converter for Retrofit
    implementation(libs.logging.interceptor) // Logging for HTTP requests

    // JSON Parsing
    implementation(libs.gson)

    //Icons
    implementation (libs.androidx.material.icons.extended)


    //ksp and dagger
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    implementation(libs.androidx.hilt.navigation.compose)

    //Room db

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)

    implementation(libs.androidx.paging.runtime.ktx) // For Kotlin use paging-runtime-ktx
    implementation(libs.androidx.paging.compose)
    implementation (libs.androidx.room.paging )

    //Maps
    implementation(libs.maps.compose)


}