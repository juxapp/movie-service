package com.movies.exceptions

import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
@Order
class RestExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(RequestInvalidException::class)
    fun handleRequestInvalidException(ex: RequestInvalidException): ResponseEntity<ApiResponse> {
        val apiError = ApiResponse(HttpStatus.BAD_REQUEST, "Request Invalid", ex.message)
        return buildResponseEntity(apiError)
    }
    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFoundException(ex: ResourceNotFoundException): ResponseEntity<ApiResponse> {
        val apiError = ApiResponse(HttpStatus.NOT_FOUND, "Unauthorized", ex.message)
        return buildResponseEntity(apiError)
    }

    @ExceptionHandler(InternalServerException::class)
    fun handleInternalServerException(ex: InternalServerException): ResponseEntity<ApiResponse> {
        val apiError = ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Error", ex.message)
        return buildResponseEntity(apiError)
    }

    private fun buildResponseEntity(apiError: ApiResponse): ResponseEntity<ApiResponse> {
        return ResponseEntity(apiError, apiError.getCode())
    }
}
