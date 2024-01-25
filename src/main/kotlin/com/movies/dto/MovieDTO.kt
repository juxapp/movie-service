package com.movies.dto

import java.io.Serializable

data class MovieDTO(
    var name: String? = null,
    var slots: List<SlotDTO>? = null,
    var screen: String? = null
): Serializable