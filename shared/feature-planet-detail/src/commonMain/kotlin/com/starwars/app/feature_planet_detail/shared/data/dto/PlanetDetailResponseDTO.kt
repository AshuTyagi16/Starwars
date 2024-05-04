package com.starwars.app.feature_planet_detail.shared.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlanetDetailResponseDTO(
    @SerialName("result")
    val result: PlanetDetailDTO
)