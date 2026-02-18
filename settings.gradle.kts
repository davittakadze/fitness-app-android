enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "BetterYou"
include(":app")
include(":core:data")
include(":core:domain")
include(":core:presentation")
include(":core-ui")
include(":feature:menu:data")
include(":feature:menu:domain")
include(":feature:menu:presentation")
include(":feature:log_in")
include(":feature:log_in:presentation")
include(":feature:log_in:domain")
include(":feature:log_in:data")
include(":feature:splash")
include(":feature:splash:presentation")
include(":feature:splash:domain")
include(":feature:splash:data")
include(":feature:profile")
include(":feature:profile:data")
include(":feature:profile:presentation")
include(":feature:register")
include(":feature:register:data")
include(":feature:register:domain")
include(":feature:register:presentation")
include(":feature:settings")
include(":feature:settings:presentation")
include(":feature:settings:domain")
include(":feature:settings:data")
include(":feature:profile:domain")
include(":util")
include(":feature:daily")
include(":feature:daily:presentation")
include(":feature:daily:domain")
include(":feature:daily:data")
include(":feature:workout:data")
include(":feature:workout:presentation")
include(":feature:workout:domain")
include(":core:model")
include(":feature:history")
include(":feature:history:data")
include(":feature:history:domain")
include(":feature:history:presentation")
include(":feature:recipes")
include(":feature:recipes:data")
include(":feature:recipes:domain")
include(":feature:recipes:presentation")
