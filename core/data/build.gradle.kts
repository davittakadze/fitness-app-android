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
}

dependencies {
    implementation(projects.core.domain)
}