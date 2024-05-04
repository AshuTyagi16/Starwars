package com.starwars.app.feature_planet_list.shared.ui

import androidx.paging.cachedIn
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.starwars.app.feature_planet_list.shared.domain.use_case.FetchPlanetListUseCase

class PlanetListScreenModel(
    fetchPlanetListUseCase: FetchPlanetListUseCase
) : ScreenModel {

    val pager = fetchPlanetListUseCase
        .fetchPlanets()
        .cachedIn(screenModelScope)
}