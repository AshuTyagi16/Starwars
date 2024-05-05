package com.starwars.app.feature_planet_detail.shared.data.local

import app.cash.sqldelight.db.SqlDriver
import app.cash.turbine.test
import com.starwars.app.core_network.shared.di.coreNetworkModule
import com.starwars.app.feature_planet_detail.shared.data.dto.PlanetDetailResponseDTO
import com.starwars.app.feature_planet_detail.shared.di.featurePlanetDetailModule
import com.starwars.app.feature_planet_detail.shared.domain.mappers.toPlanetDetail
import com.starwars.app.feature_planet_detail.shared.domain.mappers.toPlanetDetailEntity
import com.starwars.app.feature_planet_detail.shared.util.DummyPlanetDetailResponse
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

class PlanetDetailLocalDataSourceTest : KoinTest {

    private val planetDetailLocalDataSource: PlanetDetailLocalDataSource by inject()

    private val sqlDriver: SqlDriver by inject()

    private val json: Json by inject()

    private val planetDetail by lazy {
        json.decodeFromString<PlanetDetailResponseDTO>(DummyPlanetDetailResponse.planetDetailResponse)
    }

    @BeforeTest
    fun before() = runTest {
        startKoin {
            modules(
                coreNetworkModule,
                featurePlanetDetailModule,
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
    fun `test insert planet detail inserts item in database`() = runTest {
        planetDetailLocalDataSource.insertPlanetDetail(planetDetail.result.toPlanetDetail().toPlanetDetailEntity())
        planetDetailLocalDataSource.fetchPlanetDetail(planetDetail.result.uid).test {
            val item = awaitItem()
            assertEquals(
                planetDetail.result.uid,
                item?.uid
            )
            assertEquals(
                planetDetail.result.properties.name,
                item?.name
            )
        }
    }

}