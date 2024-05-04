package com.starwars.app

import android.app.Application
import cafe.adriel.voyager.core.registry.ScreenRegistry
import com.starwars.app.core_database.shared.di.coreDatabaseModule
import com.starwars.app.core_network.shared.di.networkModule
import com.starwars.app.feature_planet_detail.featurePlanetDetailScreenModule
import com.starwars.app.feature_planet_detail.shared.di.featurePlanetDetailModule
import com.starwars.app.feature_planet_list.featurePlanetListScreenModule
import com.starwars.app.feature_planet_list.shared.di.featurePlanetListModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class StarWarsApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@StarWarsApp)
            androidLogger()
            modules(
                coreDatabaseModule,
                networkModule,
                featurePlanetListModule,
                featurePlanetDetailModule
            )
        }
        ScreenRegistry {
            featurePlanetDetailScreenModule()
        }
    }
}