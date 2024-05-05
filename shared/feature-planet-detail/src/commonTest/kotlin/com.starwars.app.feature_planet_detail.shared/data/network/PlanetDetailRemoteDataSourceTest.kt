package com.starwars.app.feature_planet_detail.shared.data.network

import com.starwars.app.core_network.shared.di.coreNetworkModule
import com.starwars.app.feature_planet_detail.shared.data.dto.PlanetDetailResponseDTO
import com.starwars.app.feature_planet_detail.shared.di.featurePlanetDetailModule
import com.starwars.app.feature_planet_detail.shared.util.DummyPlanetDetailResponse
import com.starwars.core_test.testHttpEngineModule
import com.starwars.core_test.testSqliteDriverModule
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
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

class PlanetDetailRemoteDataSourceTest : KoinTest {

    private val planetDetailRemoteDataSource: PlanetDetailRemoteDataSource by inject()

    private val json: Json by inject()

    private val planetDetail by lazy {
        json.decodeFromString<PlanetDetailResponseDTO>(DummyPlanetDetailResponse.planetDetailResponse)
    }

    @BeforeTest
    fun before() {
        startKoin {
            modules(
                coreNetworkModule,
                featurePlanetDetailModule,
                testSqliteDriverModule
            )
        }
    }

    @Test
    fun `fetch planet detail success`() = runTest {
        val module = testHttpEngineModule(
            isSuccess = true,
            getSuccessResponse = {
                DummyPlanetDetailResponse.planetDetailResponse
            }
        )

        loadKoinModules(module)
        val planetDetailResponse = planetDetailRemoteDataSource.fetchPlanetDetails("1")
        assertEquals(
            planetDetailResponse.data,
            planetDetail
        )
        unloadKoinModules(module)
    }

    @Test
    fun `fetch planet detail failure`() = runTest {
        val module = testHttpEngineModule(isSuccess = false)
        loadKoinModules(module)
        val planetDetailResponse = planetDetailRemoteDataSource.fetchPlanetDetails("invalid_id")
        assertEquals(
            planetDetailResponse.data,
            null
        )
        unloadKoinModules(module)
    }

    @AfterTest
    fun after() {
        stopKoin()
    }


}