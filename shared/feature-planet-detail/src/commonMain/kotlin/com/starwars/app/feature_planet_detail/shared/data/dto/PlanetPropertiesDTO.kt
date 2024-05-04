package com.starwars.app.feature_planet_detail.shared.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class PlanetPropertiesDTO(
    @SerialName("climate")
    val climate: String,

    @SerialName("diameter")
    val diameter: String,

    @SerialName("gravity")
    val gravity: String,

    @SerialName("name")
    val name: String,

    @SerialName("orbital_period")
    val orbitalPeriod: String,

    @SerialName("population")
    val population: String,

    @SerialName("rotation_period")
    val rotationPeriod: String,

    @SerialName("surface_water")
    val surfaceWater: String,

    @SerialName("terrain")
    val terrain: String,
)