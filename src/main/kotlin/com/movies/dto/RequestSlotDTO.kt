package com.movies.dto


data class RequestSlotDTO (
    var id: Long?= null,
    var date: String?= null,
    var time: String?= null,
    var movieId: Long?= null
)