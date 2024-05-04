package com.starwars.app.feature_planet_detail.shared.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlanetDetailDTO(
    @SerialName("properties")
    val properties: PlanetPropertiesDTO,

    @SerialName("uid")
    val uid: String
)