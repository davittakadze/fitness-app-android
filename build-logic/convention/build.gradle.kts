plugins {
    `kotlin-dsl`
}

group = "com.betteryou.buildlogic"

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    implementation(libs.kotlin.serialization.gradle.plugin)
}
gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "betteryou.android.application"
            implementationClass = "com.betteryou.build_logic.AndroidApplicationConventionPlugin"
        }

        register("androidLibrary") {
            id = "betteryou.android.library"
            implementationClass = "com.betteryou.build_logic.AndroidLibraryConventionPlugin"
        }

        register("androidHilt") {
            id = "betteryou.android.hilt"
            implementationClass = "com.betteryou.build_logic.AndroidHiltConventionPlugin"
        }

        register("androidCompose") {
            id = "betteryou.android.compose"
            implementationClass = "com.betteryou.build_logic.AndroidComposeConventionPlugin"
        }

        register("androidFirebase") {
            id = "betteryou.android.firebase"
            implementationClass = "com.betteryou.build_logic.AndroidFirebaseConventionPlugin"
        }

        register("androidSerialization") {
            id = "betteryou.android.serialization"
            implementationClass = "com.betteryou.build_logic.AndroidSerializationConventionPlugin"
        }

        register("androidRoom") {
            id = "betteryou.android.room"
            implementationClass = "com.betteryou.build_logic.AndroidRoomConventionPlugin"
        }

        register("androidDataStore") {
            id = "betteryou.android.datastore"
            implementationClass = "com.betteryou.build_logic.AndroidDataStoreConventionPlugin"
        }

        register("androidNetwork") {
            id = "betteryou.android.network"
            implementationClass = "com.betteryou.build_logic.AndroidNetworkConventionPlugin"
        }

        register("androidRetrofit") {
            id = "betteryou.android.retrofit"
            implementationClass = "com.betteryou.build_logic.AndroidRetrofitConventionPlugin"
        }

        register("androidWorkManager") {
            id = "betteryou.android.workmanager"
            implementationClass = "com.betteryou.build_logic.AndroidWorkManagerConventionPlugin"
        }
    }
}