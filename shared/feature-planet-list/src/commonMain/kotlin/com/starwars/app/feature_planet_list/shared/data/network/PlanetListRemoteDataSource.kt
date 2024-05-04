package com.starwars.app.feature_planet_list.shared.data.network

import com.starwars.app.core_network.shared.data.model.ApiResponse
import com.starwars.app.feature_planet_list.shared.data.dto.PlanetsResponseDTO

internal interface PlanetListRemoteDataSource {

    suspend fun fetchPlanets(page: Int, limit: Int): ApiResponse<PlanetsResponseDTO?>

}