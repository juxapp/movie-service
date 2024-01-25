package com.movies.exceptions

import com.fasterxml.jackson.annotation.JsonGetter
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonSetter
import org.springframework.http.HttpStatus

class ApiResponse constructor(
    private var code: HttpStatus? = null,
    private var key: String? = null,
    private var description: String? = null
) {

    constructor(statusCode: HttpStatus?) : this(statusCode,
        "Unexpected error",
        "No error description provided")

    @JsonGetter("code")
    fun getStatusCode(): Int? {
        return code?.value()
    }

    @JsonSetter("code")
    fun setStatusCodeValue(statusCodeValue: Int?) {
        code = statusCodeValue?.let { HttpStatus.valueOf(it) }
    }

    @JsonIgnore
    fun getCode(): HttpStatus {
        return code!!
    }

    fun getKey(): String? {
        return key
    }

    fun getDescription(): String? {
        return description
    }
}
