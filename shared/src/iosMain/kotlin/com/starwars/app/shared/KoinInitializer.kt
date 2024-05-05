package com.starwars.app.shared

import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(getSharedModules())
    }
}