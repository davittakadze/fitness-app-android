plugins {
    id("org.jetbrains.kotlin.jvm")
    id("betteryou.android.serialization")
    id("betteryou.android.datastore")
}
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}


dependencies {
    implementation(libs.dagger)
    implementation(libs.kotlinx.coroutines.play.services)
}