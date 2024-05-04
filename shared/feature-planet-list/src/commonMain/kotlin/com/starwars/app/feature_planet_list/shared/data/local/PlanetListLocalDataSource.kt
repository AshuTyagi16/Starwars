package com.starwars.app.feature_planet_list.shared.data.local

import com.starwars.app.coredatabase.shared.PlanetEntity

internal interface PlanetListLocalDataSource {

    suspend fun insertPlanets(planets: List<PlanetEntity>)

    suspend fun fetchPlanetCount(): Long
}