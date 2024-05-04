package com.starwars.app.feature_planet_detail.shared.domain.mappers

import com.starwars.app.feature_planet_detail.shared.data.dto.PlanetDetailDTO
import com.starwars.app.feature_planet_detail.shared.data.dto.PlanetPropertiesDTO
import com.starwars.app.feature_planet_detail.shared.domain.model.PlanetDetail
import com.starwars.app.feature_planet_detail.shared.domain.model.PlanetProperties
import com.starwars.app.coredatabase.shared.PlanetDetailEntity

internal fun PlanetDetailDTO.toPlanetDetail(): PlanetDetail {
    return PlanetDetail(
        properties = properties.toPlanetProperties(),
        uid = uid
    )
}

internal fun PlanetDetailEntity.toPlanetDetail(): PlanetDetail {
    return PlanetDetail(
        uid = uid,
        properties = PlanetProperties(
            climate = climate,
            diameter = diameter,
            gravity = gravity,
            name = name,
            orbitalPeriod = orbitalPeriod,
            population = population,
            rotationPeriod = rotationPeriod,
            surfaceWater = surfaceWater,
            terrain = terrain
        )
    )
}

internal fun PlanetDetail.toPlanetDetailEntity(): PlanetDetailEntity {
    return PlanetDetailEntity(
        uid = uid,
        climate = properties.climate,
        diameter = properties.diameter,
        gravity = properties.gravity,
        name = properties.name,
        orbitalPeriod = properties.orbitalPeriod,
        population = properties.population,
        rotationPeriod = properties.rotationPeriod,
        surfaceWater = properties.surfaceWater,
        terrain = properties.terrain
    )
}

internal fun PlanetPropertiesDTO.toPlanetProperties(): PlanetProperties {
    return PlanetProperties(
        climate = climate,
        diameter = diameter,
        gravity = gravity,
        name = name,
        orbitalPeriod = orbitalPeriod,
        population = population,
        rotationPeriod = rotationPeriod,
        surfaceWater = surfaceWater,
        terrain = terrain
    )
}