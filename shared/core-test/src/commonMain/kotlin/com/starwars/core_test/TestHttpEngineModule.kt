package com.starwars.core_test

import com.starwars.app.core_network.shared.data.network.HttpClientApi
import com.starwars.app.core_network.shared.util.NetworkConstants
import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockEngineConfig
import io.ktor.client.engine.mock.respond
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import org.koin.dsl.module

fun testHttpEngineModule(
    isSuccess: Boolean,
    getSuccessResponse: (path: String) -> String = { "" }
) = module {
    single<HttpClientEngine> {
        MockEngine(
            MockEngineConfig().also {
                it.addHandler { request ->
                    respond(
                        content = if (isSuccess) getSuccessResponse(request.url.encodedPath) else "",
                        status = if (isSuccess) HttpStatusCode.OK else HttpStatusCode.NotFound,
                        headers = headersOf(
                            HttpHeaders.ContentType,
                            ContentType.Application.Json.toString()
                        ),
                    )
                }
            }
        )
    }
    single<Ktorfit> {
        val httpClientApi: HttpClientApi = get()
        Ktorfit.Builder()
            .httpClient(
                httpClientApi.getHttpClient(
                    baseUrl = NetworkConstants.BASE_URL,
                    shouldEnableLogging = true,
                    engine = get()
                )
            )
            .build()
    }
}