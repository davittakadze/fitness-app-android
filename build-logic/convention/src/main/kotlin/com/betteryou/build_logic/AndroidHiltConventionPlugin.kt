package com.betteryou.build_logic

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidHiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.devtools.ksp")
                apply("dagger.hilt.android.plugin")
            }

            dependencies {
                add("implementation", findLibrary("hilt.android"))
                add("ksp", findLibrary("hilt.compiler"))
            }
        }
    }
}

internal fun Project.findLibrary(alias: String) = extensions.getByType<VersionCatalogsExtension>()
    .named("libs")
    .findLibrary(alias)
    .get()