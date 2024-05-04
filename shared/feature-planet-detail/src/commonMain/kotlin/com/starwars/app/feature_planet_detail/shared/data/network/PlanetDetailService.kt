package com.starwars.app.feature_planet_detail.shared.data.network

import com.starwars.app.feature_planet_detail.shared.data.dto.PlanetDetailResponseDTO
import de.jensklingenberg.ktorfit.Response
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Path

internal interface PlanetDetailService {

    @GET("/api/planets/{uid}")
    suspend fun fetchPlanetDetail(@Path("uid") uid: String): Response<PlanetDetailResponseDTO>
}