package com.starwars.app.feature_planet_list.shared.data.network

import com.starwars.app.feature_planet_list.shared.data.dto.PlanetsResponseDTO
import de.jensklingenberg.ktorfit.Response
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Query

internal interface PlanetListService {

    @GET("/api/planets")
    suspend fun fetchPlanets(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Response<PlanetsResponseDTO>
}