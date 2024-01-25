package com.movies.controller

import com.movies.dto.MovieDTO
import com.movies.dto.RequestMovieDTO
import com.movies.service.MovieService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

@RestController
@RequestMapping("/api/v1")
public class MovieController (
    var movieService: MovieService
){
    @GetMapping("/movies")
    fun getAllMovies(
        @RequestParam("startDate", required = false)  startDate:String?,
        @RequestParam("screenType", required = false)  screenType:String?): ResponseEntity<List<MovieDTO>> {
        val movies = movieService.getMovies(startDate, screenType)
        return ResponseEntity.ok().body(movies)
    }

    @GetMapping("/movies/{id}")
    fun getMovieById( @PathVariable id:Long): ResponseEntity<List<MovieDTO>>{
        val movie =movieService.getMovieById(id)
        return ResponseEntity.ok().body(movie)
    }

    @PutMapping("/movies/{id}")
    fun updateMovie( @PathVariable id:Long,@RequestBody movieDTO: RequestMovieDTO): ResponseEntity<String> {
        movieService.updateMovie(id,movieDTO)
        return ResponseEntity.ok().body("success")
    }

}
