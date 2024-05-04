package com.starwars.app.core_network.shared.util

import kotlin.time.Duration.Companion.seconds

object NetworkConstants {

    internal const val NETWORK_LOG_TAG = "#Network_Logs#"

    object ErrorMessage {
        const val APP_UNDER_MAINTENANCE = "App under maintenance"
        const val PLEASE_CHECK_YOUR_INTERNET_CONNECTION = "Please check your internet connection"
        const val SOMETHING_WENT_WRONG = "Something went wrong"
    }

    object ErrorCodes {
        const val DATA_SERIALIZATION_EXCEPTION = 1001
        const val SERVER_RESPONSE_EXCEPTION = 1002
        const val TIMEOUT_EXCEPTION = 1003
        const val CANCELLATION_EXCEPTION = 1004
        const val GENERIC_EXCEPTION = 1005
    }

    internal object Timeout {
        val REQUEST_TIMEOUT_MILLIS = 10.seconds.inWholeMilliseconds
        val CONNECT_TIMEOUT_MILLIS = 10.seconds.inWholeMilliseconds
        val SOCKET_TIMEOUT_MILLIS = 10.seconds.inWholeMilliseconds
    }

    const val BASE_URL = "www.swapi.tech"

}