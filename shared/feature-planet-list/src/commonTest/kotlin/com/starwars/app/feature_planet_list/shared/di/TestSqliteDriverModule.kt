package com.starwars.app.feature_planet_list.shared.di

import app.cash.sqldelight.db.SqlDriver
import com.starwars.app.core_database.shared.PlanetDatabase
import org.koin.dsl.module

val testSqliteDriverModule = module {
    single<SqlDriver> {
        getSqliteTestDriver()
    }
    single {
        PlanetDatabase(driver = get())
    }
}