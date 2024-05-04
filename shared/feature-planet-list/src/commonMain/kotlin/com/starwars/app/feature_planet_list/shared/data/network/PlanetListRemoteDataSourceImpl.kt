package com.starwars.app.feature_planet_list.shared.data.network

import com.starwars.app.core_network.shared.data.base.BaseDataSource

internal class PlanetListRemoteDataSourceImpl(
    private val planetListService: PlanetListService
) : BaseDataSource(), PlanetListRemoteDataSource {

    override suspend fun fetchPlanets(page: Int, limit: Int) = getResult {
        planetListService.fetchPlanets(page, limit)
    }

}