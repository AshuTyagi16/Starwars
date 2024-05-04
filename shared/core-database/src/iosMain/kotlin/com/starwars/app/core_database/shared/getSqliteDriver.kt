package com.starwars.app.core_database.shared

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver

actual fun getSqliteDriver(): SqlDriver = NativeSqliteDriver(
    PlanetDatabase.Schema, CoreDatabaseConstants.DATABASE_NAME
)