package com.movies.exceptions



class RequestInvalidException : RuntimeException {
    constructor(msg: String) : super(msg)
}
class InternalServerException : RuntimeException {
    constructor(msg: String) : super(msg)
}

class ResourceNotFoundException : RuntimeException {
    constructor(msg: String) : super(msg)
}