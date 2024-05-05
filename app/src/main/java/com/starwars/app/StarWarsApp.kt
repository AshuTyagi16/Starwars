package com.starwars.app

import android.app.Application
import cafe.adriel.voyager.core.registry.ScreenRegistry
import com.starwars.app.feature_planet_detail.featurePlanetDetailScreenModule
import com.starwars.app.shared.getSharedModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class StarWarsApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@StarWarsApp)
            androidLogger()
            modules(getSharedModules())
        }
        ScreenRegistry {
            featurePlanetDetailScreenModule()
        }
    }
}