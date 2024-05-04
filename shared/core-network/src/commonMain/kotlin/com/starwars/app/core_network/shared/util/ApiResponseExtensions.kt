package com.starwars.app.core_network.shared.util

import com.starwars.app.core_network.shared.data.model.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

suspend fun <T> Flow<ApiResponse<T>>.collectCustom(
    onLoading: suspend () -> Unit,
    onSuccess: suspend (data: T?) -> Unit,
    onError: suspend (errorMessage: String) -> Unit
) {
    this.collectLatest {
        when (it.status) {
            ApiResponse.Status.IDLE -> {
                // Ignore this case
            }

            ApiResponse.Status.LOADING -> {
                onLoading.invoke()
            }

            ApiResponse.Status.ERROR -> {
                onError.invoke(it.errorMessage.orEmpty())
            }

            ApiResponse.Status.SUCCESS -> {
                onSuccess.invoke(it.data)
            }
        }
    }
}

fun <T> ApiResponse<T>.isSuccess(): Boolean = this.status == ApiResponse.Status.SUCCESS