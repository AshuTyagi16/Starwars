package com.starwars.app.core_navigation

import cafe.adriel.voyager.core.registry.ScreenProvider

sealed interface SharedScreen : ScreenProvider {

    data object PlanetList : SharedScreen

    data class PlanetDetail(val uid: String) : SharedScreen
}