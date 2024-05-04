package com.starwars.app.feature_planet_list.shared.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlanetDTO(
    @SerialName("name")
    val name: String,

    @SerialName("uid")
    val uid: String
)