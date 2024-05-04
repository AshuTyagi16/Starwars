package com.starwars.app.feature_planet_list.shared.domain.use_case

import androidx.paging.testing.asSnapshot
import com.starwars.app.core_database.shared.di.coreDatabaseModule
import com.starwars.app.core_network.shared.di.networkModule
import com.starwars.app.feature_planet_list.shared.di.featurePlanetListModule
import com.starwars.core_test.testHttpEngineModule
import com.starwars.app.feature_planet_list.shared.util.DummyPlanetListResponse
import com.starwars.core_test.testSqliteDriverModule
import kotlinx.coroutines.test.runTest
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.context.unloadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class FetchPlanetListUseCaseTest : KoinTest {

    private val fetchPlanetListUseCase: FetchPlanetListUseCase by inject()

    @BeforeTest
    fun setup() {
        startKoin {
            modules(
                coreDatabaseModule,
                networkModule,
                featurePlanetListModule,
                testSqliteDriverModule
            )
        }
    }

    @AfterTest
    fun cleanup() {
        stopKoin()
    }

    @Test
    fun `fetchPlanets success return paging data with items`() = runTest {
        val module = testHttpEngineModule(
            isSuccess = true,
            getSuccessResponse = {
                DummyPlanetListResponse.planetListResponse
            }
        )
        loadKoinModules(module)
        val itemSnapshot = fetchPlanetListUseCase.fetchPlanets().asSnapshot {
            scrollTo(index = 10)
        }
        assertEquals(10, itemSnapshot.size)
        assertEquals("Tatooine", itemSnapshot.first().name)
        unloadKoinModules(module)
    }

    @Test
    fun `fetchPlanets failure return empty paging data`() = runTest {
        val module = testHttpEngineModule(isSuccess = false)
        loadKoinModules(module)
        assertFailsWith<Throwable>(
            block = {
                fetchPlanetListUseCase.fetchPlanets().asSnapshot {
                    scrollTo(index = 10)
                }
            }
        )
        unloadKoinModules(module)
    }
}