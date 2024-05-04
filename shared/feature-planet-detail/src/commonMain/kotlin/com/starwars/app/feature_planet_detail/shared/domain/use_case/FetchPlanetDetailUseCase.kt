package com.starwars.app.feature_planet_detail.shared.domain.use_case

import com.starwars.app.feature_planet_detail.shared.domain.model.PlanetDetail
import kotlinx.coroutines.flow.Flow
import org.mobilenativefoundation.store.store5.StoreReadResponse

fun interface FetchPlanetDetailUseCase {

    suspend fun invoke(uid: String): Flow<StoreReadResponse<PlanetDetail?>>
}