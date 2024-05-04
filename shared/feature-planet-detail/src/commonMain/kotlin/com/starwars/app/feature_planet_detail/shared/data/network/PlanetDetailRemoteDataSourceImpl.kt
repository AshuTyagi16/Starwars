package com.starwars.app.feature_planet_detail.shared.data.network

import com.starwars.app.core_network.shared.data.base.BaseDataSource

internal class PlanetDetailRemoteDataSourceImpl(
    private val planetDetailService: PlanetDetailService
) : PlanetDetailRemoteDataSource, BaseDataSource() {
    override suspend fun fetchPlanetDetails(uid: String) = getResult {
        planetDetailService.fetchPlanetDetail(uid)
    }
}