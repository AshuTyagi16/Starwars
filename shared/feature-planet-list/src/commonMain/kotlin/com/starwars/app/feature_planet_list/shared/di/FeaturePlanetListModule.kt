package com.starwars.app.feature_planet_list.shared.di

import com.starwars.app.core_database.shared.PlanetDatabase
import com.starwars.app.coredatabase.shared.PlanetListQueries
import com.starwars.app.feature_planet_list.shared.data.local.PlanetListLocalDataSource
import com.starwars.app.feature_planet_list.shared.data.local.PlanetListLocalDataSourceImpl
import com.starwars.app.feature_planet_list.shared.data.network.PlanetListRemoteDataSource
import com.starwars.app.feature_planet_list.shared.data.network.PlanetListRemoteDataSourceImpl
import com.starwars.app.feature_planet_list.shared.data.network.PlanetListService
import com.starwars.app.feature_planet_list.shared.domain.repository.PlanetListRemoteMediator
import com.starwars.app.feature_planet_list.shared.domain.use_case.FetchPlanetListUseCase
import com.starwars.app.feature_planet_list.shared.domain.use_case.FetchPlanetListUseCaseImpl
import com.starwars.app.feature_planet_list.shared.ui.PlanetListScreenModel
import de.jensklingenberg.ktorfit.Ktorfit
import org.koin.dsl.module

val featurePlanetListModule = module {

    single<PlanetListService> {
        val ktorfit: Ktorfit = get()
        ktorfit.create()
    }

    single<PlanetListRemoteDataSource> {
        PlanetListRemoteDataSourceImpl(planetListService = get())
    }

    single<PlanetListLocalDataSource> {
        PlanetListLocalDataSourceImpl(dbQueries = get())
    }

    single<FetchPlanetListUseCase> {
        FetchPlanetListUseCaseImpl(
            planetListRemoteMediator = get(),
            dbQueries = get()
        )
    }

    single<PlanetListRemoteMediator> {
        PlanetListRemoteMediator(
            planetListLocalDataSource = get(),
            planetListRemoteDataSource = get()
        )
    }

    single<PlanetListQueries> {
        val database: PlanetDatabase = get()
        database.planetListQueries
    }

    factory {
        PlanetListScreenModel(
            fetchPlanetListUseCase = get()
        )
    }

}