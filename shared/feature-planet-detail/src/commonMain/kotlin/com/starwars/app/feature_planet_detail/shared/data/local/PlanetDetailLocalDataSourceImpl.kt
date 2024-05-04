package com.starwars.app.feature_planet_detail.shared.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToOne
import com.starwars.app.coredatabase.shared.PlanetDetailEntity
import com.starwars.app.coredatabase.shared.PlanetDetailQueries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

internal class PlanetDetailLocalDataSourceImpl(
    private val dbQueries: PlanetDetailQueries
) : PlanetDetailLocalDataSource {
    override suspend fun insertPlanetDetail(planetDetailEntity: PlanetDetailEntity) {
        dbQueries.transaction {
            dbQueries.insertPlanetDetail(
                uid = planetDetailEntity.uid,
                climate = planetDetailEntity.climate,
                diameter = planetDetailEntity.diameter,
                gravity = planetDetailEntity.gravity,
                name = planetDetailEntity.name,
                orbitalPeriod = planetDetailEntity.orbitalPeriod,
                population = planetDetailEntity.population,
                rotationPeriod = planetDetailEntity.rotationPeriod,
                surfaceWater = planetDetailEntity.surfaceWater,
                terrain = planetDetailEntity.terrain
            )
        }
    }

    override fun fetchPlanetDetail(uid: String) =
        dbQueries.fetchPlanetDetail(uid)
            .asFlow()
            .mapToOne(Dispatchers.IO)
}