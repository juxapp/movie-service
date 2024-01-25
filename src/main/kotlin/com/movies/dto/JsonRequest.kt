package com.movies.dto

data class JsonData(val data: List<JsonRequest>)

data class JsonRequest(
    var name: String? = null,
    var slots: List<SlotDTO>,
    var screen: String? = null
)