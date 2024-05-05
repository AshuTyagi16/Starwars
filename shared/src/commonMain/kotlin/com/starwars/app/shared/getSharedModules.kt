package com.starwars.app.shared

import com.starwars.app.core_database.shared.di.coreDatabaseModule
import com.starwars.app.core_network.shared.di.coreNetworkModule
import com.starwars.app.feature_planet_detail.shared.di.featurePlanetDetailModule
import com.starwars.app.feature_planet_list.shared.di.featurePlanetListModule

fun getSharedModules()  = listOf(
    coreNetworkModule,
    coreDatabaseModule,
    featurePlanetListModule,
    featurePlanetDetailModule
)