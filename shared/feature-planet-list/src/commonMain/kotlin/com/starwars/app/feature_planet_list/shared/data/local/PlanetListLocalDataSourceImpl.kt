package com.starwars.app.feature_planet_list.shared.data.local

import com.starwars.app.coredatabase.shared.PlanetEntity
import com.starwars.app.coredatabase.shared.PlanetListQueries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

internal class PlanetListLocalDataSourceImpl(
    private val dbQueries: PlanetListQueries
) : PlanetListLocalDataSource {

    override suspend fun insertPlanets(planets: List<PlanetEntity>) = withContext(Dispatchers.IO) {
        dbQueries.transaction {
            planets.forEach {
                dbQueries.insertPlanet(it.uid, it.name)
            }
        }
    }

    override suspend fun fetchPlanetCount() = dbQueries.countPlanets().executeAsOne()
}