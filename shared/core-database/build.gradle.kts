plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.sqldelight)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = libs.versions.jvmTarget.get()
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "core-database"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {

            // Sqldelight DB
            implementation(libs.bundles.sqldelight.common)

            implementation(libs.bundles.koin.common)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        androidMain.dependencies {
            implementation(libs.bundles.sqldelight.android)

            implementation(libs.app.startup)
        }
        iosMain.dependencies {
            implementation(libs.bundles.sqldelight.native)
        }
    }
}

val modulePackageName = "com.starwars.app.core_database.shared"

android {
    namespace = modulePackageName
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

sqldelight {
    databases {
        create("PlanetDatabase") {
            packageName.set(modulePackageName)
        }
    }
}

task("testClasses").doLast {
    println("This is a dummy testClasses task")
}