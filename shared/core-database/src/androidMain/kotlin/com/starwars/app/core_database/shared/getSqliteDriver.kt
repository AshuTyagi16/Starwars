package com.starwars.app.core_database.shared

import android.content.Context
import androidx.startup.Initializer
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver

private var appContext: Context? = null

actual fun getSqliteDriver(): SqlDriver = AndroidSqliteDriver(
    PlanetDatabase.Schema,
    context = appContext!!,
    name = CoreDatabaseConstants.DATABASE_NAME
)

internal class SqliteDriverInitializer : Initializer<Context> {
    override fun create(context: Context): Context =
        context.applicationContext.also { appContext = it }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}