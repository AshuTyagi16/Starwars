package com.starwars.app.feature_planet_list.shared.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class PlanetsResponseDTO(
    @SerialName("results")
    val results: List<PlanetDTO>,

    @SerialName("total_pages")
    val totalPages: Int,

    @SerialName("total_records")
    val totalRecords: Int
)