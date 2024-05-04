package com.starwars.app.feature_planet_list.shared.data.network

import com.starwars.app.core_network.shared.data.model.ApiResponse
import com.starwars.app.core_network.shared.di.networkModule
import com.starwars.app.feature_planet_list.shared.di.featurePlanetListModule
import com.starwars.app.feature_planet_list.shared.di.testHttpEngineModule
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

class PlanetListRemoteDataSourceTest : KoinTest {

    private val planetListRemoteDataSource: PlanetListRemoteDataSource by inject<PlanetListRemoteDataSource>()

    @BeforeTest
    fun before() {
        startKoin {
            modules(
                networkModule,
                featurePlanetListModule
            )
        }
    }

    @AfterTest
    fun after() {
        stopKoin()
    }

    @Test
    fun fetchCurrenciesSuccess() = runTest {
        val module = testHttpEngineModule(isSuccess = true)

        loadKoinModules(module)
        val result = planetListRemoteDataSource.fetchPlanets(0, 10)

        assertEquals(ApiResponse.Status.SUCCESS, result.status)

        assertEquals(true, result.data?.results.isNullOrEmpty().not())

        unloadKoinModules(module)
    }

    @Test
    fun fetchCurrenciesFailure() = runTest {
        val module = testHttpEngineModule(isSuccess = false)
        loadKoinModules(module)
        val result = planetListRemoteDataSource.fetchPlanets(0, 10)

        assertEquals(ApiResponse.Status.ERROR, result.status)

        unloadKoinModules(module)
    }


}