<h1 align="center">Starwars</h1>
<p align="center">
  🗡️ Starwars is a Kotlin Multiplatform sample that mirrors the architecture of a production-level app!
</p>
<p align="center">
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
  <a href="https://android-arsenal.com/api?level=21"><img alt="API" src="https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat"/></a>
</p>

## Link to download APK: 

[Click here to download Starwars](https://i.diawi.com/YmnF7H)


## Tech stack & Open-source libraries

- **Minimum SDK level**
  - Android 21
  - iOS 15  
  
- **Language**
   - Android + Shared - [Kotlin](https://kotlinlang.org/)
   - IOS - [Swift](https://github.com/apple/swift)

- **UI Framework**
   - Android - [Jetpack Compose](https://developer.android.com/jetpack/compose)
   - IOS - [SwiftUI](https://developer.apple.com/xcode/swiftui/)
  
- **Architecture**
  - MVI Architecture (Model - View - Intent)
  - [Repository Pattern](https://proandroiddev.com/the-real-repository-pattern-in-android-efba8662b754)
  - [Clean Code Architecture](https://proandroiddev.com/why-you-need-use-cases-interactors-142e8a6fe576) (Usecase + mappers)
    
- [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) for asynchronous.
- [Ktor](https://github.com/ktorio/ktor/tree/main): HTTP Client Franework for REST Apis.
- [SKIE](https://skie.touchlab.co): SKIE is a tool for Kotlin Multiplatform development that enhances the Swift API published from Kotlin.
- [KMMBridge](https://kmmbridge.touchlab.co): KMMBridge is a set of Gradle tooling that facilitates publishing and consuming pre-built KMM Xcode Framework binaries.
- [Kotlinx.serialization](https://github.com/square/moshi/): A modern JSON serialization library for Kotlin.
- [Kermit](https://github.com/touchlab/Kermit): Multiplatform centralized logging utility.
- [Paging3](https://github.com/cashapp/multiplatform-paging): Multiplatform Paging Library
- [Koin](https://github.com/InsertKoinIO/koin) : A pragmatic lightweight dependency injection framework for Kotlin & Kotlin Multiplatform
- [Store](https://github.com/MobileNativeFoundation/Store) : A Kotlin Multiplatform library for building network-resilient applications (Build offline first apps)
- [SqlDelight](https://github.com/cashapp/sqldelight): Multiplatform SQLite Database (Generates typesafe Kotlin APIs from SQL)

----

<table>
<tr>
  
  <h3>Sample Videos</h3>
  
<td>
  
<h4>Android</h4>

<img src="https://github.com/AshuTyagi16/Starwars/assets/18714067/3dd1849d-e686-484c-a377-c5e4437ce0f8" width="320"></img>

</td>
<td>
  
<h4>IOS</h4>

<img src="https://github.com/AshuTyagi16/Starwars/assets/18714067/6609e703-4c5a-4864-943e-05a26ee7aef2" width="320"></img>

</td>

</tr>
</table>

## Features
- Planet Listing on HomePage
- Planet Detail Page
- Paging Support in planet listing screen using [Paging3](https://github.com/cashapp/multiplatform-paging)
- Loading / Error footer support while paging + retry functionlity in listing (supported in both android & ios)
- Offline first (Load from in-memory cache -> disk -> network) using [Store](https://github.com/MobileNativeFoundation/Store)
- Multi module support in android & shared modules
- Better support for suspend + flows + sealed classes using [SKIE](https://skie.touchlab.co)
- Publish IOS Binary as POD framework using [KMMBridge](https://kmmbridge.touchlab.co)
- Dependency injection using [Koin](https://github.com/InsertKoinIO/koin)
- Database caching using [SqlDelight](https://github.com/cashapp/sqldelight)
- Unit tests for the shared code.


## Modularization

**Starwars** adopted modularization strategies below:

- **Reusability**: Modulizing reusable codes properly enable opportunities for code sharing and limits code accessibility in other modules at the same time.
- **Parallel Building**: Each module can be run in parallel and it reduces the build time.
- **Strict visibility control**: Modules restrict to expose dedicated components and access to other layers, so it prevents they're being used outside the module
- **Decentralized focusing**: Each developer team can assign their dedicated module and they can focus on their own modules.

**NOTE**: The same modularization strategies are used for [shared](https://github.com/AshuTyagi16/Starwars/tree/main/shared) module as well

For more information, check out the [Guide to app modularization](https://developer.android.com/topic/modularization).

## How to run IOS app?

- Step 1: Open termianl inside the root folder of the project
- Step 2: Run the following gradle task: ```./gradlew linkPodDebugFrameworkIosX64``` (It will create the pod framework for the shared code)
- Step 3: Navigate to ```iosApp/``` folder & run the following command: ```export LOCAL_KOTLIN_PATH=../shared```
- Step 4: Run ```pod install```
- Step 5: Now open xcode & open ```ios.xcworkspace``` file inside ```iosApp/``` folder.
- Step 6: Run the app in simulator

NOTE: Please make sure you have [cocoapods](https://guides.cocoapods.org/using/getting-started.html) installed.


## TODO
- Shared resource support using [moko-resources](https://github.com/icerockdev/moko-resources)
- MultiModule structure in iOS project

# License
```xml
Designed and developed by 2024 AshuTyagi16 (Ashu Tyagi)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
