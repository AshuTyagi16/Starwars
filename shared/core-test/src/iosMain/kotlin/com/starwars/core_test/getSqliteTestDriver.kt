package com.starwars.core_test

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.inMemoryDriver
import com.starwars.app.core_database.shared.PlanetDatabase
internal actual fun getSqliteTestDriver(): SqlDriver = inMemoryDriver(PlanetDatabase.Schema)