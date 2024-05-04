package com.starwars.app.core_network.shared.data.network

import co.touchlab.kermit.Logger
import co.touchlab.kermit.platformLogWriter
import com.starwars.app.core_network.shared.data.network.HttpClientApi
import com.starwars.app.core_network.shared.util.NetworkConstants
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import io.ktor.client.plugins.logging.Logger as KtorLogger
import com.starwars.app.core_network.shared.util.NetworkConstants.Timeout

internal class HttpClientApiImpl(
    private val json: Json
) : HttpClientApi {

    init {
        Logger.setLogWriters(platformLogWriter())
    }

    override fun getHttpClient(
        baseUrl: String,
        shouldEnableLogging: Boolean,
        engine: HttpClientEngine
    ): HttpClient {

        // Setup logger
        val customLogger = Logger.withTag(NetworkConstants.NETWORK_LOG_TAG)

        return HttpClient(engine) {

            expectSuccess = true

            //Default Request
            defaultRequest {

                val urlString = URLProtocol.HTTPS.name.plus("://").plus(baseUrl)
                url(urlString)

                // Content type
                contentType(ContentType.Application.Json)
            }

            //Timeout
            install(HttpTimeout) {
                requestTimeoutMillis = Timeout.REQUEST_TIMEOUT_MILLIS
                socketTimeoutMillis = Timeout.SOCKET_TIMEOUT_MILLIS
                connectTimeoutMillis = Timeout.CONNECT_TIMEOUT_MILLIS
            }

            //Logging
            if (shouldEnableLogging) {
                install(Logging) {
                    logger = object : KtorLogger {
                        override fun log(message: String) {
                            customLogger.d(message)
                        }
                    }
                    level = LogLevel.ALL
                }
            }

            //Response Observer
            install(ResponseObserver) {
                onResponse {
                    if (shouldEnableLogging) {
                        customLogger.d(it.bodyAsText())
                    }
                }
            }

            //Serialization
            install(ContentNegotiation) {
                json(json)
            }
        }
    }

}