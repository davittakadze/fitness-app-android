package com.betteryou.build_logic

import com.android.build.gradle.LibraryExtension
import com.betteryou.build_logic.convention.configureAndroidCompose
import com.betteryou.build_logic.convention.configureAndroidLifecycle
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val extension = extensions.getByType<LibraryExtension>()
            configureAndroidCompose(extension)

            configureAndroidLifecycle()
        }
    }
}