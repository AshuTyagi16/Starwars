import co.touchlab.skie.configuration.FlowInterop
import co.touchlab.skie.configuration.EnumInterop
import co.touchlab.skie.configuration.SealedInterop
import co.touchlab.skie.configuration.SuspendInterop
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kmm.bridge)
    alias(libs.plugins.skie)
    `maven-publish`
}

version = "0.1"

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = libs.versions.jvmTarget.get()
            }
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            // Shared Core Base Module
            api(projects.shared.coreBase)

            // Shared Core Database Module
            api(projects.shared.coreDatabase)

            // Shared Core Network Module
            api(projects.shared.coreNetwork)

            // Shared Feature Planet List Module
            api(projects.shared.featurePlanetList)

            // Shared Feature Planet Detail Module
            api(projects.shared.featurePlanetDetail)

            api(libs.bundles.koin.common)

            api(libs.stately)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }

    cocoapods {
        summary = "Starwars Kmp Shared Binary"
        homepage = "https://github.com/AshuTyagi16/Starwars"
        ios.deploymentTarget = "13.0"
        extraSpecAttributes["libraries"] = "'c++', 'sqlite3'"
        license = "BSD"
        extraSpecAttributes["swift_version"] = "\"5.9.2\""
        framework {

            baseName = "shared"

            // Shared Core Base Module
            export(project(":shared:core-base"))

            // Shared Core Database Module
            export(projects.shared.coreDatabase)

            // Shared Core Network Module
            export(projects.shared.coreNetwork)

            // Shared Feature Planet List Module
            export(projects.shared.featurePlanetList)

            // Shared Feature Planet Detail Module
            export(projects.shared.featurePlanetDetail)

            isStatic = true

            binaryOption("bundleId", "com.starwars.app.shared")
        }
    }
}

android {
    namespace = "com.starwars.app.shared"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

kmmbridge {
    mavenPublishArtifacts()
    spm()
    cocoapods("git@github.com:AshuTyagi16/StarwarsKmpPodspec.git")
    buildType.set(NativeBuildType.DEBUG)
}

skie {
    features {
        group {
            FlowInterop.Enabled(true)
            coroutinesInterop.set(true)
            SuspendInterop.Enabled(true)
            EnumInterop.Enabled(true)
            SealedInterop.Enabled(true)
        }
    }
}