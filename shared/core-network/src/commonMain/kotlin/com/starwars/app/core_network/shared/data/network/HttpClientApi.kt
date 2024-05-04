package com.starwars.app.core_network.shared.data.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine

interface HttpClientApi {

    fun getHttpClient(
        baseUrl: String,
        shouldEnableLogging: Boolean,
        engine: HttpClientEngine
    ): HttpClient
}