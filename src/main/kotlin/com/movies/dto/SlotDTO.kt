package com.movies.dto

import java.io.Serializable


data class SlotDTO (
    var date: String?= null,
    var time: String?= null
): Serializable