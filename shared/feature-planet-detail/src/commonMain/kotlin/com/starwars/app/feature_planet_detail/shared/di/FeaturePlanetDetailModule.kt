package com.starwars.app.feature_planet_detail.shared.di

import com.starwars.app.core_database.shared.PlanetDatabase
import com.starwars.app.coredatabase.shared.PlanetDetailQueries
import com.starwars.app.feature_planet_detail.shared.data.local.PlanetDetailLocalDataSource
import com.starwars.app.feature_planet_detail.shared.data.local.PlanetDetailLocalDataSourceImpl
import com.starwars.app.feature_planet_detail.shared.data.network.PlanetDetailRemoteDataSource
import com.starwars.app.feature_planet_detail.shared.data.network.PlanetDetailRemoteDataSourceImpl
import com.starwars.app.feature_planet_detail.shared.data.network.PlanetDetailService
import com.starwars.app.feature_planet_detail.shared.data.repository.PlanetDetailRepository
import com.starwars.app.feature_planet_detail.shared.domain.repository.PlanetDetailRepositoryImpl
import com.starwars.app.feature_planet_detail.shared.domain.use_case.FetchPlanetDetailUseCase
import com.starwars.app.feature_planet_detail.shared.ui.PlanetDetailScreenModel
import de.jensklingenberg.ktorfit.Ktorfit
import org.koin.dsl.module

val featurePlanetDetailModule = module {
    single<PlanetDetailService> {
        val ktorfit: Ktorfit = get()
        ktorfit.create()
    }

    single<PlanetDetailRemoteDataSource> {
        PlanetDetailRemoteDataSourceImpl(planetDetailService = get())
    }

    single<PlanetDetailLocalDataSource> {
        PlanetDetailLocalDataSourceImpl(dbQueries = get())
    }

    single<PlanetDetailRepository> {
        PlanetDetailRepositoryImpl(
            planetDetailLocalDataSource = get(),
            planetDetailRemoteDataSource = get()
        )
    }

    single<FetchPlanetDetailUseCase> {
        FetchPlanetDetailUseCase(get<PlanetDetailRepository>()::fetchPlanetDetail)
    }

    single<PlanetDetailQueries> {
        val database: PlanetDatabase = get()
        database.planetDetailQueries
    }

    factory {
        PlanetDetailScreenModel(
            fetchPlanetDetailUseCase = get()
        )
    }
}