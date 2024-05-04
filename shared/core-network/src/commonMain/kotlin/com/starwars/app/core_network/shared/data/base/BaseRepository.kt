package com.starwars.app.core_network.shared.data.base

import com.starwars.app.core_network.shared.data.model.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface BaseRepository {
    suspend fun <T> getFlowResult(call: suspend () -> ApiResponse<T>): Flow<ApiResponse<T>> =
        flow {
            emit(ApiResponse.loading())
            val result = call.invoke()
            emit(result)
        }
}