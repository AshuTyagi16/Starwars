package com.starwars.app.feature_planet_list.shared.domain.use_case

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import app.cash.sqldelight.paging3.QueryPagingSource
import com.starwars.app.coredatabase.shared.PlanetListQueries
import com.starwars.app.feature_planet_list.shared.domain.mapper.toPlanet
import com.starwars.app.feature_planet_list.shared.domain.repository.PlanetListRemoteMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalPagingApi::class)
internal class FetchPlanetListUseCaseImpl(
    private val planetListRemoteMediator: PlanetListRemoteMediator,
    private val dbQueries: PlanetListQueries
) : FetchPlanetListUseCase {

    override fun fetchPlanets() = Pager(
        config = PagingConfig(
            pageSize = 10,
            enablePlaceholders = true,
            initialLoadSize = 10
        ),
        remoteMediator = planetListRemoteMediator,
    ) {
        QueryPagingSource(
            countQuery = dbQueries.countPlanets(),
            transacter = dbQueries,
            context = Dispatchers.IO,
            queryProvider = dbQueries::fetchPlanet
        )
    }
        .flow
        .flowOn(Dispatchers.IO)
        .map {
            it.map {
                it.toPlanet()
            }
        }
}