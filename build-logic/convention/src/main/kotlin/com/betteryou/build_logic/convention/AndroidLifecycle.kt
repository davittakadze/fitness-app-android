package com.betteryou.build_logic.convention

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidLifecycle() {
    dependencies {
        add("implementation", libs.findLibrary("androidx.lifecycle.viewmodel.ktx").get())
        add("implementation", libs.findLibrary("androidx.lifecycle.runtime.ktx").get())
        add("implementation", libs.findLibrary("androidx.lifecycle.runtime.compose").get())
    }
}