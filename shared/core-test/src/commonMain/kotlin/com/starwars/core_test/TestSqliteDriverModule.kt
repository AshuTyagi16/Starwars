package com.starwars.core_test

import app.cash.sqldelight.db.SqlDriver
import com.starwars.app.core_database.shared.PlanetDatabase
import org.koin.dsl.module

val testSqliteDriverModule = module {
    single<SqlDriver> {
        getSqliteTestDriver2()
    }
    single {
        PlanetDatabase(driver = get())
    }
}