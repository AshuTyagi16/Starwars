package com.starwars.app.feature_planet_list.shared.data.local

import app.cash.sqldelight.db.SqlDriver
import com.starwars.app.core_network.shared.di.networkModule
import com.starwars.app.coredatabase.shared.PlanetEntity
import com.starwars.app.feature_planet_list.shared.di.featurePlanetListModule
import com.starwars.app.feature_planet_list.shared.util.DummyPlanetListResponse
import com.starwars.core_test.testSqliteDriverModule
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
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
        json.parseToJsonElement(DummyPlanetListResponse.planetListResponse)
            .jsonObject["results"]?.jsonArray
            ?.map {
                PlanetEntity(
                    uid = it.jsonObject["uid"]!!.jsonPrimitive.content.toLong(),
                    name = it.jsonObject["name"]!!.jsonPrimitive.content
                )
            }
            .orEmpty()
    }

    @BeforeTest
    fun before() = runTest {
        startKoin {
            modules(
                networkModule,
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