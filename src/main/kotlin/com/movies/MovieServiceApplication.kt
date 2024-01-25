package com.movies

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
@SpringBootApplication
@EnableCaching
class MovieServiceApplication

fun main(args: Array<String>) {
	runApplication<MovieServiceApplication>(*args)
}
