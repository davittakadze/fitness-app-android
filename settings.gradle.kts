enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
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
include(":core_ui")
include(":core_res")
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
