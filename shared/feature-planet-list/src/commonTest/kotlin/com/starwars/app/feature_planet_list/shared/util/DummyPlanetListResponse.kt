package com.starwars.app.feature_planet_list.shared.util

internal object DummyPlanetListResponse {

    val planetListResponse = """
        {
        "message": "ok",
        "total_records": 60,
        "total_pages": 6,
        "previous": null,
        "next": "https://swapi.tech/api/planets?page=2&limit=10",
        "results": [
        {
        "uid": "1",
        "name": "Tatooine",
        "url": "https://www.swapi.tech/api/planets/1"
        },
        {
        "uid": "2",
        "name": "Alderaan",
        "url": "https://www.swapi.tech/api/planets/2"
        },
        {
        "uid": "3",
        "name": "Yavin IV",
        "url": "https://www.swapi.tech/api/planets/3"
        },
        {
        "uid": "4",
        "name": "Hoth",
        "url": "https://www.swapi.tech/api/planets/4"
        },
        {
        "uid": "5",
        "name": "Dagobah",
        "url": "https://www.swapi.tech/api/planets/5"
        },
        {
        "uid": "6",
        "name": "Bespin",
        "url": "https://www.swapi.tech/api/planets/6"
        },
        {
        "uid": "7",
        "name": "Endor",
        "url": "https://www.swapi.tech/api/planets/7"
        },
        {
        "uid": "8",
        "name": "Naboo",
        "url": "https://www.swapi.tech/api/planets/8"
        },
        {
        "uid": "9",
        "name": "Coruscant",
        "url": "https://www.swapi.tech/api/planets/9"
        },
        {
        "uid": "10",
        "name": "Kamino",
        "url": "https://www.swapi.tech/api/planets/10"
        }
        ]
        }
    """.trimIndent()

    fun getResponseForEndpoint(endpoint: String): String {
        return when {
            endpoint.contains("/api/planets") -> planetListResponse
            else -> ""
        }
    }
}