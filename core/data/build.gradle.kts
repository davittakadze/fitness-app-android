plugins {
    id("betteryou.android.library")
    id("betteryou.android.hilt")
    id("betteryou.android.firebase")
    id("betteryou.android.serialization")
    id("betteryou.android.room")
    id("betteryou.android.datastore")
    id("betteryou.android.network")
}

android {
    namespace = "com.example.betteryou.data"

    defaultConfig {
        buildConfigField("String", "BASE_URL", "\"https://mocki.io/v1/\"")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URL", "\"https://mocki.io/v1/\"")
        }
        getByName("debug") {
            buildConfigField("String", "BASE_URL", "\"https://mocki.io/v1/\"")
        }
    }

    buildFeatures {
        buildConfig = true
    }

}

dependencies {
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation(libs.logging.interceptor)

    implementation(projects.core.domain)
    implementation(projects.core.model)
}