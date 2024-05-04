package com.starwars.app.core_network.shared.util

import com.starwars.app.core_network.shared.data.model.ApiResponse

suspend inline fun <T, U> ApiResponse<T?>.mapToDomain(
    crossinline dataMapper: suspend (t: T?) -> U?
): ApiResponse<U?> =
    when (this.status) {
        ApiResponse.Status.SUCCESS -> {
            ApiResponse.success(
                data = dataMapper.invoke(this.data),
            )
        }

        ApiResponse.Status.IDLE -> ApiResponse.idle()
        ApiResponse.Status.LOADING -> ApiResponse.loading()
        ApiResponse.Status.ERROR -> ApiResponse.error(
            errorMessage = this.errorMessage,
            errorCode = this.errorCode
        )
    }