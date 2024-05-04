package com.starwars.app.feature_planet_detail.shared.data.network

import com.starwars.app.core_network.shared.data.model.ApiResponse
import com.starwars.app.feature_planet_detail.shared.data.dto.PlanetDetailResponseDTO

internal interface PlanetDetailRemoteDataSource {

    suspend fun fetchPlanetDetails(uid: String): ApiResponse<PlanetDetailResponseDTO?>
}