package com.movies.dto

data class RequestMovieDTO (
    var id : Long ?= null,
    var name: String? = null,
    var slots: List<RequestSlotDTO>? = null,
    var screen: String? = null
)