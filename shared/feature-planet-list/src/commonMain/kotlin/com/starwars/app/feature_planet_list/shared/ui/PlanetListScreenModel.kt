package com.starwars.app.feature_planet_list.shared.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.starwars.app.feature_planet_list.shared.domain.use_case.FetchPlanetListUseCase

class PlanetListScreenModel(
    fetchPlanetListUseCase: FetchPlanetListUseCase
) : ViewModel() {

    val pager = fetchPlanetListUseCase
        .fetchPlanets()
        .cachedIn(viewModelScope)
}