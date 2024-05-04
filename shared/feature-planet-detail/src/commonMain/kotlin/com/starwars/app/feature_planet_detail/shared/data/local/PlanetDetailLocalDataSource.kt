package com.starwars.app.feature_planet_detail.shared.data.local

import com.starwars.app.coredatabase.shared.PlanetDetailEntity
import kotlinx.coroutines.flow.Flow

internal interface PlanetDetailLocalDataSource {

    suspend fun insertPlanetDetail(planetDetailEntity: PlanetDetailEntity)

    fun fetchPlanetDetail(uid: String): Flow<PlanetDetailEntity?>
}