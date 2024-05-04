dependencyResolutionManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
    }

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
}
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
rootProject.name = "StarWars"
include(":app")
include(":shared")
include(":shared:core-network")
include(":shared:feature-planet-list")
include(":feature-planet-list")
include(":shared:core-base")
include(":shared:feature-planet-detail")
include(":feature-planet-detail")
include(":core-navigation")
include(":shared:core-database")
include(":shared:core-test")
