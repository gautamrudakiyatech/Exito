plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
//    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.exito"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.exito"
        minSdk = 21
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    // Retrofit for network requests
    implementation(libs.squareup.retrofit)
    // Converter to convert JSON data to Kotlin objects (using Gson)
    implementation(libs.retrofit2.converter.gson)

    // Room for local database
    implementation(libs.androidx.room.runtime)
//    ksp(libs.room.compiler)  // Use annotation processing with Kotlin
    // If you prefer to use Kotlin Symbol Processing (KSP), there is an alternative

    // Kotlin Coroutines for asynchronous tasks
    implementation(libs.kotlinx.coroutines.android)

    // Lifecycle & ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.livedata.ktx)


}