package com.starwars.app.feature_planet_detail.shared.util

internal object DummyPlanetDetailResponse {

    val planetDetailResponse = """
        {
          "message": "ok",
          "result": {
            "properties": {
              "diameter": "10465",
              "rotation_period": "23",
              "orbital_period": "304",
              "gravity": "1 standard",
              "population": "200000",
              "climate": "arid",
              "terrain": "desert",
              "surface_water": "1",
              "created": "2024-05-03T22:37:38.658Z",
              "edited": "2024-05-03T22:37:38.658Z",
              "name": "Tatooine",
              "url": "https://www.swapi.tech/api/planets/1"
            },
            "description": "A planet.",
            "_id": "5f7254c11b7dfa00041c6fae",
            "uid": "1",
            "__v": 0
          }
        }
    """.trimIndent()
}