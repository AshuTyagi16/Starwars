package com.starwars.app.core_database.shared.di

import app.cash.sqldelight.db.SqlDriver
import com.starwars.app.core_database.shared.PlanetDatabase
import com.starwars.app.core_database.shared.getSqliteDriver
import org.koin.dsl.module

val coreDatabaseModule = module {
    single<SqlDriver> {
        getSqliteDriver()
    }
    single {
        PlanetDatabase(driver = get())
    }
}