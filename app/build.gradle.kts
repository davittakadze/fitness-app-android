plugins {
    id("betteryou.android.application")
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.androidx.navigation.safeargs.kotlin)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.protobuf)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.betteryou"

    defaultConfig {
        applicationId = "com.example.homework3"
        minSdk = 31
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures{
        viewBinding=true
        buildConfig=true
    }
}

dependencies {
    // Datastore
    implementation(libs.androidx.datastore.preferences)

    // 2. Coroutines
    implementation(libs.kotlinx.coroutines.android)

    // 3. Lifecycle
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Android & UI
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.core.splashscreen)

    // Compose
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)

    // DI hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.google.auth)
    implementation(libs.firebase.auth)

    // Project Modules (Dependencies between modules)
    implementation(projects.core.data)
    implementation(projects.core.domain)
    implementation(projects.core.presentation)
    implementation(projects.coreUi)

    implementation(projects.feature.menu.presentation)
    implementation(projects.feature.menu.domain)
    implementation(projects.feature.menu.data)

    implementation(projects.feature.logIn.presentation)
    implementation(projects.feature.logIn.domain)
    implementation(projects.feature.logIn.data)

    implementation(projects.feature.splash.data)
    implementation(projects.feature.splash.domain)
    implementation(projects.feature.splash.presentation)

    implementation(projects.feature.profile.presentation)
    implementation(projects.feature.profile.data)
    implementation(projects.feature.profile.domain)

    implementation(projects.feature.register.presentation)
    implementation(projects.feature.register.data)
    implementation(projects.feature.register.domain)

    implementation(projects.feature.settings.presentation)
    implementation(projects.feature.settings.data)
    implementation(projects.feature.settings.domain)

    implementation(projects.feature.daily.presentation)
    implementation(projects.feature.workout.data)
    implementation(projects.feature.daily.data)
    implementation(projects.feature.daily.domain)

    implementation(projects.feature.workout.presentation)

    implementation(projects.feature.recipes.presentation)

}
protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.23.4"
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                create("java") { option("lite") }
            }
        }
    }
}

