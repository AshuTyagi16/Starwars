package com.starwars.app.core_network.shared.data.base

import com.starwars.app.core_network.shared.data.model.ApiResponse
import com.starwars.app.core_network.shared.util.NetworkConstants.ErrorCodes
import com.starwars.app.core_network.shared.util.NetworkConstants.ErrorMessage
import de.jensklingenberg.ktorfit.Response
import io.ktor.client.call.body
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.JsonConvertException
import io.ktor.util.network.UnresolvedAddressException
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.serialization.SerializationException

abstract class BaseDataSource {

    protected suspend inline fun <reified T> getResult(apiCall: () -> Response<T>): ApiResponse<T?> {
        try {
            val response = apiCall.invoke()
            return if (response.isSuccessful) {
                val body = response.body()
                ApiResponse.success(
                    data = body
                )
            } else {
                ApiResponse.error(
                    errorMessage = ErrorMessage.SOMETHING_WENT_WRONG,
                    errorCode = response.code
                )
            }
        } catch (e: Exception) {
            when (e) {
                is ClientRequestException -> {
                    return ApiResponse.error(
                        errorMessage = e.message,
                        errorCode = e.response.status.value
                    )
                }

                is ServerResponseException -> {
                    return ApiResponse.error(
                        errorMessage = ErrorMessage.APP_UNDER_MAINTENANCE,
                        errorCode = ErrorCodes.SERVER_RESPONSE_EXCEPTION
                    )
                }

                is ConnectTimeoutException -> {
                    return ApiResponse.error(
                        errorMessage = ErrorMessage.PLEASE_CHECK_YOUR_INTERNET_CONNECTION,
                        errorCode = ErrorCodes.TIMEOUT_EXCEPTION
                    )
                }

                is SocketTimeoutException -> {
                    return ApiResponse.error(
                        errorMessage = ErrorMessage.PLEASE_CHECK_YOUR_INTERNET_CONNECTION,
                        errorCode = ErrorCodes.TIMEOUT_EXCEPTION
                    )
                }

                is IOException -> {
                    return ApiResponse.error(
                        errorMessage = ErrorMessage.PLEASE_CHECK_YOUR_INTERNET_CONNECTION,
                        errorCode = ErrorCodes.TIMEOUT_EXCEPTION
                    )
                }

                is UnresolvedAddressException -> {
                    return ApiResponse.error(
                        errorMessage = ErrorMessage.PLEASE_CHECK_YOUR_INTERNET_CONNECTION,
                        errorCode = ErrorCodes.TIMEOUT_EXCEPTION
                    )
                }

                is SerializationException -> {
                    return ApiResponse.error(
                        errorMessage = ErrorMessage.SOMETHING_WENT_WRONG,
                        errorCode = ErrorCodes.DATA_SERIALIZATION_EXCEPTION
                    )
                }

                is JsonConvertException -> {
                    return ApiResponse.error(
                        errorMessage = ErrorMessage.SOMETHING_WENT_WRONG,
                        errorCode = ErrorCodes.DATA_SERIALIZATION_EXCEPTION
                    )
                }

                is CancellationException -> {
                    return ApiResponse.error(
                        errorMessage = "", // This happens when api call gets cancelled.. we don't want to show any error in this case
                        errorCode = ErrorCodes.CANCELLATION_EXCEPTION
                    )
                }

                else -> {
                    return ApiResponse.error(
                        errorMessage = ErrorMessage.SOMETHING_WENT_WRONG,
                        errorCode = ErrorCodes.GENERIC_EXCEPTION
                    )
                }
            }
        }
    }

    protected suspend inline fun <reified E> HttpResponse.parseErrorBody(): E? = try {
        this.body<E>()
    } catch (e: Exception) {
        null
    }

    protected suspend fun <T> retryIOs(
        times: Int = Int.MAX_VALUE,
        initialDelay: Long = 100,
        maxDelay: Long = 1000,
        factor: Double = 2.0,
        block: suspend () -> T,
        shouldRetry: (result: T) -> Boolean
    ): T {
        var currentDelay = initialDelay
        repeat(times - 1) {
            val result = block()
            if (shouldRetry(result).not()) {
                return result
            }
            delay(currentDelay)
            currentDelay = (currentDelay * factor).toLong().coerceAtMost(maxDelay)
        }
        return block()
    }
}