package com.starwars.app.feature_planet_detail.shared.domain.repository

import com.starwars.app.core_network.shared.util.isSuccess
import com.starwars.app.core_network.shared.util.mapToDomain
import com.starwars.app.feature_planet_detail.shared.data.local.PlanetDetailLocalDataSource
import com.starwars.app.feature_planet_detail.shared.data.network.PlanetDetailRemoteDataSource
import com.starwars.app.feature_planet_detail.shared.data.repository.PlanetDetailRepository
import com.starwars.app.feature_planet_detail.shared.domain.mappers.toPlanetDetail
import com.starwars.app.feature_planet_detail.shared.domain.mappers.toPlanetDetailEntity
import com.starwars.app.feature_planet_detail.shared.domain.model.PlanetDetail
import kotlinx.coroutines.flow.map
import org.mobilenativefoundation.store.store5.Fetcher
import org.mobilenativefoundation.store.store5.MemoryPolicy
import org.mobilenativefoundation.store.store5.SourceOfTruth
import org.mobilenativefoundation.store.store5.StoreBuilder
import org.mobilenativefoundation.store.store5.StoreReadRequest
import kotlin.time.Duration.Companion.seconds

internal class PlanetDetailRepositoryImpl(
    private val planetDetailLocalDataSource: PlanetDetailLocalDataSource,
    private val planetDetailRemoteDataSource: PlanetDetailRemoteDataSource
) : PlanetDetailRepository {

    private val store =
        StoreBuilder.from<String, PlanetDetail, PlanetDetail>(
            fetcher = Fetcher.of {
                val result = planetDetailRemoteDataSource.fetchPlanetDetails(it)
                    .mapToDomain {
                        it?.result?.toPlanetDetail()
                    }
                if (result.isSuccess()) {
                    result.data!!
                } else {
                    error(result.errorMessage.orEmpty())
                }
            },
            sourceOfTruth = SourceOfTruth.of<String, PlanetDetail, PlanetDetail>(
                reader = {
                    planetDetailLocalDataSource.fetchPlanetDetail(it)
                        .map {
                            it?.toPlanetDetail()
                        }
                },
                writer = { _, input ->
                    planetDetailLocalDataSource.insertPlanetDetail(input.toPlanetDetailEntity())
                }
            )
        )
            .cachePolicy(
                MemoryPolicy.builder<String, PlanetDetail>()
                    .setExpireAfterWrite(10.seconds)
                    .build()
            )
            .build()

    override suspend fun fetchPlanetDetail(uid: String) =
        store.stream(
            StoreReadRequest.cached(
                key = uid,
                refresh = false
            )
        )

}