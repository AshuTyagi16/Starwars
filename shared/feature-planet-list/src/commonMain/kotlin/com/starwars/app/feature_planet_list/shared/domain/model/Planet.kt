package com.starwars.app.feature_planet_list.shared.domain.model

import com.starwars.app.core_base.shared.CustomSerializable

data class Planet(
    val name: String,
    val uid: String
): CustomSerializable