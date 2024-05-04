plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.ksp)
    alias(libs.plugins.ktorfit)
    alias(libs.plugins.kotlinSerialization)
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
            baseName = "feature-planet-detail"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {

            // Shared Core Base Module
            api(projects.shared.coreBase)

            // Shared Core Network Module
            implementation(projects.shared.coreNetwork)

            // Shared Core Database Module
            implementation(projects.shared.coreDatabase)

            // Ktorfit
            implementation(libs.ktorfit)

            // Kotlinx Serialization
            implementation(libs.kotlin.serialization)

            // Koin DI
            implementation(libs.bundles.koin.common)

            // Voyager ScreenModel
            implementation(libs.voyager.screenmodel)

            // SqlDelight DB
            implementation(libs.bundles.sqldelight.common)

            api(libs.store)

            implementation(projects.shared.coreTest)
        }
        commonTest.dependencies {
            implementation(libs.bundles.shared.commonTest)
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

dependencies {
    with(libs.ktorfit.ksp) {
        add("kspCommonMainMetadata", this)
        add("kspAndroid", this)
        add("kspAndroidTest", this)
        add("kspIosX64", this)
        add("kspIosX64Test", this)
        add("kspIosArm64", this)
        add("kspIosArm64Test", this)
        add("kspIosSimulatorArm64", this)
        add("kspIosSimulatorArm64Test", this)
    }
}

val modulePackageName = "com.starwars.app.feature_planet_detail.shared"

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