package com.starwars.app.feature_planet_list

import cafe.adriel.voyager.core.registry.screenModule
import com.starwars.app.core_navigation.SharedScreen

val featurePlanetListScreenModule = screenModule {
    register<SharedScreen.PlanetList> {
        PlanetListScreen
    }
}