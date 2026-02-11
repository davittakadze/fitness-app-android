package com.betteryou.build_logic

import com.betteryou.build_logic.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidFirebaseConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            dependencies {
                val bom = libs.findLibrary("firebase-bom").get()
                add("implementation", platform(bom))
                add("implementation", libs.findLibrary("firebase-auth").get())
                add("implementation", libs.findLibrary("firebase-firestore").get())
                add("implementation", libs.findLibrary("firebase-storage").get())
            }
        }
    }
}