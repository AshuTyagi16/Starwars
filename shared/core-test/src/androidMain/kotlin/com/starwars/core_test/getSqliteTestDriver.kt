package com.starwars.core_test

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.starwars.app.core_database.shared.PlanetDatabase

internal actual fun getSqliteTestDriver(): SqlDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
    .also { PlanetDatabase.Schema.create(it) }