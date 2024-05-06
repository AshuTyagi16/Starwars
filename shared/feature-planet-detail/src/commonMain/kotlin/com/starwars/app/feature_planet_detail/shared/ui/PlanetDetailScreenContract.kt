package com.starwars.app.feature_planet_detail.shared.ui

import com.starwars.app.core_base.shared.model.UiEffect
import com.starwars.app.core_base.shared.model.UiEvent
import com.starwars.app.core_base.shared.model.UiState
import com.starwars.app.feature_planet_detail.shared.domain.model.PlanetDetail

class PlanetDetailScreenContract {

    sealed class State: UiState {
        data object Loading: State()
        data class Success(
            val planetDetail: PlanetDetail?
        ): State()
    }

    sealed interface Effect : UiEffect {
        data class ShowToastEvent(
            val message: String
        ) : Effect
    }

    sealed interface Event : UiEvent {
        data class FetchPlanetDetail(
            val uid: String
        ) : Event
    }
}