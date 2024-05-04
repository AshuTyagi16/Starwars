package com.starwars.app.core_network.shared.data.model

data class ApiResponse<out T>(
    val status: Status,
    val data: T? = null,
    val errorMessage: String? = null,
    val errorCode: Int? = null
) {
    enum class Status {
        IDLE,
        LOADING,
        ERROR,
        SUCCESS
    }

    companion object {

        fun <T> idle(): ApiResponse<T> {
            return ApiResponse(status = Status.IDLE)
        }

        fun <T> loading(): ApiResponse<T> {
            return ApiResponse(status = Status.LOADING)
        }

        fun <T> success(
            data: T
        ): ApiResponse<T> {
            return ApiResponse(
                status = Status.SUCCESS,
                data = data
            )
        }

        fun <T> error(
            errorMessage: String?,
            errorCode: Int?
        ): ApiResponse<T> {
            return ApiResponse(
                status = Status.ERROR,
                errorMessage = errorMessage,
                errorCode = errorCode
            )
        }
    }
}