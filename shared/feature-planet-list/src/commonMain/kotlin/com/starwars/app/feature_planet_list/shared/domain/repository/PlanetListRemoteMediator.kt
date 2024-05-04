package com.starwars.app.feature_planet_list.shared.domain.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.starwars.app.core_network.shared.util.isSuccess
import com.starwars.app.feature_planet_list.shared.data.local.PlanetListLocalDataSource
import com.starwars.app.feature_planet_list.shared.data.network.PlanetListRemoteDataSource
import com.starwars.app.feature_planet_list.shared.domain.mapper.toPlanetEntityList
import com.starwars.app.coredatabase.shared.PlanetEntity

@OptIn(ExperimentalPagingApi::class)
class PlanetListRemoteMediator(
    private val planetListLocalDataSource: PlanetListLocalDataSource,
    private val planetListRemoteDataSource: PlanetListRemoteDataSource
) : RemoteMediator<Int, PlanetEntity>() {

    companion object {
        private const val START_PAGE = 0
    }

    private var nextPage = START_PAGE

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PlanetEntity>
    ): MediatorResult {

        val currentPage = when (loadType) {
            LoadType.REFRESH -> {
                null
            }

            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = true)
            }

            LoadType.APPEND -> {
                if(state.lastItemOrNull() == null) {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                nextPage
            }
        } ?: START_PAGE

        val response = planetListRemoteDataSource.fetchPlanets(currentPage, state.config.pageSize)

        return if (response.isSuccess()) {
            planetListLocalDataSource.insertPlanets(response.data?.toPlanetEntityList().orEmpty())
            nextPage++
            MediatorResult.Success(
                endOfPaginationReached = nextPage > (response.data?.totalPages ?: 0)
            )
        } else {
            MediatorResult.Error(Throwable(response.errorMessage))
        }
    }

}