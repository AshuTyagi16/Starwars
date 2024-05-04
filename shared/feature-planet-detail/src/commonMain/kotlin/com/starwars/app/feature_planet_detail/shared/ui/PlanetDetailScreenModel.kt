package com.starwars.app.feature_planet_detail.shared.ui

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.starwars.app.feature_planet_detail.shared.domain.model.PlanetDetail
import com.starwars.app.feature_planet_detail.shared.domain.use_case.FetchPlanetDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.mobilenativefoundation.store.store5.StoreReadResponse

class PlanetDetailScreenModel(
    private val uid: String,
    private val fetchPlanetDetailUseCase: FetchPlanetDetailUseCase
) : ScreenModel {

    private val _planetDetailResponse = MutableStateFlow<StoreReadResponse<PlanetDetail?>?>(null)
    val planetDetailResponse = _planetDetailResponse.asStateFlow()

    init {
        fetchPlanetDetail()
    }

    fun fetchPlanetDetail() {
        screenModelScope.launch {
            fetchPlanetDetailUseCase.invoke(uid)
                .collectLatest {
                    _planetDetailResponse.emit(it)
                }
        }
    }
}