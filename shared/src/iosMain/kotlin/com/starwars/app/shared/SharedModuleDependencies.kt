package com.starwars.app.shared

import com.starwars.app.feature_planet_detail.shared.ui.PlanetDetailViewModel
import com.starwars.app.feature_planet_list.shared.ui.PlanetListViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object SharedModuleDependencies : KoinComponent {

    val planetListViewModel: PlanetListViewModel by inject()

    val planetDetailViewModel: PlanetDetailViewModel by inject()
}