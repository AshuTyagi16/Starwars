package com.starwars.app.feature_planet_detail.shared.data.repository

import app.cash.turbine.test
import com.starwars.app.core_network.shared.di.coreNetworkModule
import com.starwars.app.feature_planet_detail.shared.di.featurePlanetDetailModule
import com.starwars.app.feature_planet_detail.shared.util.DummyPlanetDetailResponse
import com.starwars.core_test.testHttpEngineModule
import com.starwars.core_test.testSqliteDriverModule
import kotlinx.coroutines.test.runTest
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.context.unloadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject
import org.mobilenativefoundation.store.store5.StoreReadResponse
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PlanetDetailRepositoryTest : KoinTest {

    private val planetDetailRepository: PlanetDetailRepository by inject()

    @BeforeTest
    fun setup() {
        startKoin {
            modules(
                coreNetworkModule,
                testSqliteDriverModule,
                featurePlanetDetailModule,
            )
        }
    }

    @Test
    fun `fetchPlanetDetail should return a planet detail`() = runTest {
        val module = testHttpEngineModule(
            isSuccess = true,
            getSuccessResponse = {
                DummyPlanetDetailResponse.planetDetailResponse
            }
        )
        loadKoinModules(module)

        planetDetailRepository.fetchPlanetDetail("1").test {
            val firstItem = awaitItem()
            assertEquals(
                firstItem.dataOrNull() == null,
                true
            ) // Initial Data From In-Memory Cache Which will be null

            assertTrue {
                awaitItem() is StoreReadResponse.Loading
            } // Invokes Fetcher & Emits Loading

            val finalItem = awaitItem()

            assertEquals(finalItem.dataOrNull() != null, true) // Fresh Data From APi
            assertEquals(
                finalItem.dataOrNull()?.uid,
                "1"
            ) // Fresh Data From APi

            cancelAndIgnoreRemainingEvents()
        }

        unloadKoinModules(module)
    }

    @AfterTest
    fun stop() {
        stopKoin()
    }
}