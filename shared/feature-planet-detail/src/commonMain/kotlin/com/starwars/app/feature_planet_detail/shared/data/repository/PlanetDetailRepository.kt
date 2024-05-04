package com.starwars.app.feature_planet_detail.shared.data.repository

import com.starwars.app.feature_planet_detail.shared.domain.model.PlanetDetail
import kotlinx.coroutines.flow.Flow
import org.mobilenativefoundation.store.store5.StoreReadResponse

internal interface PlanetDetailRepository {

    suspend fun fetchPlanetDetail(uid: String): Flow<StoreReadResponse<PlanetDetail?>>
}