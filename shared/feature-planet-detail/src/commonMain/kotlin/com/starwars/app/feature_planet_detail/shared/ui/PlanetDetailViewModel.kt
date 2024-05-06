package com.starwars.app.feature_planet_detail.shared.ui

import androidx.lifecycle.viewModelScope
import com.starwars.app.core_base.shared.BaseViewModel
import com.starwars.app.feature_planet_detail.shared.domain.use_case.FetchPlanetDetailUseCase
import com.starwars.app.feature_planet_detail.shared.ui.PlanetDetailScreenContract.Effect
import com.starwars.app.feature_planet_detail.shared.ui.PlanetDetailScreenContract.Event
import com.starwars.app.feature_planet_detail.shared.ui.PlanetDetailScreenContract.State
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.mobilenativefoundation.store.store5.StoreReadResponse

class PlanetDetailViewModel(
    private val fetchPlanetDetailUseCase: FetchPlanetDetailUseCase
) : BaseViewModel<State, Event, Effect>() {

    private fun fetchPlanetDetail(uid: String) {
        viewModelScope.launch {
            fetchPlanetDetailUseCase.invoke(uid)
                .collectLatest {
                    when (it) {
                        is StoreReadResponse.Data -> {
                            setState {
                                State.Success(it.dataOrNull())
                            }
                        }

                        is StoreReadResponse.Error.Exception -> {
                            setEffect {
                                Effect.ShowToastEvent(it.errorMessageOrNull().orEmpty())
                            }
                        }

                        is StoreReadResponse.Error.Message -> {
                            setEffect {
                                Effect.ShowToastEvent(it.message)
                            }
                        }

                        is StoreReadResponse.Loading -> {
                            setState {
                                State.Loading
                            }
                        }

                        is StoreReadResponse.NoNewData -> {
                            // Ignore this case
                        }
                    }
                }
        }
    }

    override fun createInitialState() = State.Loading

    override fun handleEvent(event: Event) {
        when (event) {
            is Event.FetchPlanetDetail -> {
                fetchPlanetDetail(event.uid)
            }
        }
    }
}