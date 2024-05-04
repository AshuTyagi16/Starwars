package com.starwars.app.feature_planet_list.shared.domain.mapper

import com.starwars.app.feature_planet_list.shared.data.dto.PlanetDTO
import com.starwars.app.feature_planet_list.shared.data.dto.PlanetsResponseDTO
import com.starwars.app.feature_planet_list.shared.domain.model.Planet
import com.starwars.app.coredatabase.shared.PlanetEntity

fun PlanetsResponseDTO.toPlanetList(): List<Planet> {
    return this.results.map { it.toPlanet() }
}

fun PlanetsResponseDTO.toPlanetEntityList(): List<PlanetEntity> {
    return this.results.map { it.toPlanetEntity() }
}

fun PlanetDTO.toPlanet(): Planet {
    return Planet(
        uid = uid,
        name = name
    )
}

fun PlanetDTO.toPlanetEntity(): PlanetEntity {
    return PlanetEntity(
        uid = uid.toLong(),
        name = name
    )
}

fun PlanetEntity.toPlanet(): Planet {
    return Planet(
        uid = uid.toString(),
        name = name
    )
}