package com.starwars.app.feature_planet_list.shared.domain.use_case

import androidx.paging.PagingData
import com.starwars.app.feature_planet_list.shared.domain.model.Planet
import kotlinx.coroutines.flow.Flow

interface FetchPlanetListUseCase {

    fun fetchPlanets(): Flow<PagingData<Planet>>

}