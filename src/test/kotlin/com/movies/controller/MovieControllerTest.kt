package com.movies.controller

import com.movies.dto.*
import com.movies.entity.Movie
import com.movies.entity.Screen
import com.movies.entity.Slot
import com.movies.service.MovieService
import com.nhaarman.mockitokotlin2.verify
import org.hamcrest.CoreMatchers
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.result.MockMvcResultMatchers


@ExtendWith(MockitoExtension::class)
class MovieControllerTest {
    private lateinit var movieController: MovieController
    @Mock
    lateinit var  movieService: MovieService



    @BeforeEach
    fun init() {
        movieController = MovieController(movieService)
    }
    @Test
    fun getAllMovies() {
        Mockito.doReturn(getMovieList()).`when`(movieService).getMovies(any(),any())
        val response = movieController.getAllMovies(any(),any())
        assertNotNull(response)
        assertEquals(response.body,getMovieList() )
        verify(movieService).getMovies(any(),any())
    }

    private fun getMovieList(): List<MovieDTO> {
        val movie = MovieDTO("testMovie")
        val slot = SlotDTO("2024-05-20","1000")
        movie.screen="screenName"
        movie.slots= listOf(slot)
        return listOf<MovieDTO>(movie)
    }

    @Test
    fun getMovieById() {
        Mockito.doReturn(getMovieList()).`when`(movieService).getMovieById(1L)
        val response = movieController.getMovieById(1L)
        assertNotNull(response)
        assertEquals(response.body,getMovieList() )
        verify(movieService).getMovieById(1L)
    }

    @Test
    fun updateMovie() {
        val response = movieController.updateMovie(1L,getRequest())
        assertNotNull(response)
        assertEquals(response.body,"success")
    }

    private fun getRequest(): RequestMovieDTO {
        val movie = RequestMovieDTO(1L,"testMovie")
        val slot = RequestSlotDTO(1L,"2024-05-20","1000")
        movie.screen="screenName"
        movie.slots= listOf(slot)
        return movie
    }

}