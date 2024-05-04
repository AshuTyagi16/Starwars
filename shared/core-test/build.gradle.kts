plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "core-test"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.shared.coreDatabase)

            implementation(libs.bundles.sqldelight.common)
        }
        commonTest.dependencies {
            implementation(libs.bundles.shared.commonTest)
            implementation(libs.kotlin.test)
        }
        androidNativeTest.dependencies {
            implementation(libs.bundles.shared.androidTest)
        }
    }
}

android {
    namespace = "com.starwars.core_test"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    testOptions {
        unitTests {
            isReturnDefaultValues = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
