package com.starwars.app.shared

import com.starwars.app.feature_planet_detail.shared.ui.PlanetDetailScreenModel
import com.starwars.app.feature_planet_list.shared.ui.PlanetListScreenModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object SharedModuleDependencies : KoinComponent {

    val planetListScreenModel: PlanetListScreenModel by inject()

    val planetDetailScreenModel: PlanetDetailScreenModel by inject()
}