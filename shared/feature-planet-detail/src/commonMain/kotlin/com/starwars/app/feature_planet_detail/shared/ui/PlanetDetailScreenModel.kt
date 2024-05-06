package com.starwars.app.feature_planet_detail.shared.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.starwars.app.feature_planet_detail.shared.domain.model.PlanetDetail
import com.starwars.app.feature_planet_detail.shared.domain.use_case.FetchPlanetDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.mobilenativefoundation.store.store5.StoreReadResponse

class PlanetDetailScreenModel(
    private val fetchPlanetDetailUseCase: FetchPlanetDetailUseCase
) : ViewModel() {

    private val _planetDetailResponse = MutableStateFlow<StoreReadResponse<PlanetDetail?>?>(null)
    val planetDetailResponse = _planetDetailResponse.asStateFlow()

    fun fetchPlanetDetail(uid: String) {
        viewModelScope.launch {
            fetchPlanetDetailUseCase.invoke(uid)
                .collectLatest {
                    _planetDetailResponse.emit(it)
                }
        }
    }
}