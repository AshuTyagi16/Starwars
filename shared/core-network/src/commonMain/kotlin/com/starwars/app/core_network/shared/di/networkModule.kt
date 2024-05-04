package com.starwars.app.core_network.shared.di

import com.starwars.app.core_network.shared.data.network.HttpClientApi
import com.starwars.app.core_network.shared.data.network.HttpClientApiImpl
import com.starwars.app.core_network.shared.util.NetworkConstants
import de.jensklingenberg.ktorfit.Ktorfit
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.koin.dsl.module

@OptIn(ExperimentalSerializationApi::class)
val networkModule = module {

    single<Json> {
        Json {
            ignoreUnknownKeys = true
            prettyPrint = true
            isLenient = true
            useAlternativeNames = true
            encodeDefaults = true
            explicitNulls = false
            classDiscriminator = "source"
        }
    }

    single<HttpClientApi> {
        HttpClientApiImpl(json = get())
    }

    single<Ktorfit> {
        val httpClientApi : HttpClientApi = get()
        Ktorfit.Builder()
            .httpClient(httpClientApi.getHttpClient(
                baseUrl = NetworkConstants.BASE_URL,
                shouldEnableLogging = true,
                engine = getHttpClientEngine()
            ))
            .build()
    }
}