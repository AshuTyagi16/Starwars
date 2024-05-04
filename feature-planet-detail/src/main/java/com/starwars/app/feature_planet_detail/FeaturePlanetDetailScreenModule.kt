package com.starwars.app.feature_planet_detail

import cafe.adriel.voyager.core.registry.screenModule
import com.starwars.app.core_navigation.SharedScreen

val featurePlanetDetailScreenModule = screenModule {
    register<SharedScreen.PlanetDetail> { provider ->
        PlanetDetailScreen(provider.uid)
    }
}