package com.starwars.app.feature_planet_list.shared.data.local

import app.cash.sqldelight.db.SqlDriver
import com.starwars.app.core_network.shared.di.coreNetworkModule
import com.starwars.app.feature_planet_list.shared.data.dto.PlanetsResponseDTO
import com.starwars.app.feature_planet_list.shared.di.featurePlanetListModule
import com.starwars.app.feature_planet_list.shared.domain.mapper.toPlanetEntity
import com.starwars.app.feature_planet_list.shared.util.DummyPlanetListResponse
import com.starwars.core_test.testSqliteDriverModule
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class PlanetListLocalDataSourceTest : KoinTest {

    private val planetListLocalDataSource: PlanetListLocalDataSource by inject()

    private val sqlDriver: SqlDriver by inject()

    private val json: Json by inject()

    private val planetList by lazy {
        json.decodeFromString<PlanetsResponseDTO>(DummyPlanetListResponse.planetListResponse)
            .results
            .map {
                it.toPlanetEntity()
            }
    }

    @BeforeTest
    fun before() = runTest {
        startKoin {
            modules(
                coreNetworkModule,
                featurePlanetListModule,
                testSqliteDriverModule
            )
        }
    }

    @AfterTest
    fun after() {
        sqlDriver.close()
        stopKoin()
    }

    @Test
    fun `fetch all planets success`() = runTest {
        planetListLocalDataSource.insertPlanets(planetList)
        val count = planetListLocalDataSource.fetchPlanetCount()
        assertEquals(
            count,
            planetList.size.toLong()
        )
    }

}