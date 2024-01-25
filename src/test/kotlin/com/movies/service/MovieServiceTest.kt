package com.movies.service

import com.movies.dto.RequestMovieDTO
import com.movies.dto.RequestSlotDTO
import com.movies.entity.Movie
import com.movies.entity.Screen
import com.movies.entity.Slot
import com.movies.exceptions.InternalServerException
import com.movies.exceptions.ResourceNotFoundException
import com.movies.repository.MovieRepo
import com.movies.repository.ScreenRepo
import com.movies.repository.SlotRepo
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.function.Executable
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.http.ResponseEntity
import java.util.*


@ExtendWith(MockitoExtension::class)
class MovieServiceTest() {
    private lateinit var movieService: MovieService
    @Mock
    lateinit var  movieRepo: MovieRepo
    @Mock
    lateinit var slotRepo: SlotRepo
    @Mock
    lateinit var screenRepo: ScreenRepo

    @BeforeEach
    fun init() {
        movieService = MovieService(movieRepo, slotRepo,screenRepo )
    }

    @Test
    fun getMoviesSucess() {
        Mockito.`when`(slotRepo.findAllSlots(any(),any())).thenReturn(getSlotList())
        val result= movieService.getMovies("2024-05-20","IMAX")
        assertEquals(1,result?.size)
    }

    private fun getSlotList(): List<Slot>? {
              var movie = Movie("testMovie")
              movie.id=1
              var screen = Screen("IMAX")
              screen.id =1
              var slot = Slot(movieService.toTimeStamp("2024-05-20"),"1000",movie,screen)
              slot.id=1
              return listOf<Slot>(slot)
    }


    @Test
    fun getMovieById() {
        Mockito.`when`(movieRepo.findById(any())).thenReturn(getMovie())
        Mockito.`when`(slotRepo.findSlotByMovieId(1L)).thenReturn(getSlotList())
        val result= movieService.getMovieById(1)
        assertEquals(1,result?.size)
    }
    @Test
    fun getMovieByIdFailure() {
        Mockito.`when`(movieRepo.findById(any())).thenReturn(Optional.empty<Movie>())
        assertThrows(InternalServerException::class.java, Executable { movieService.getMovieById(1) })
    }

    private fun getMovie(): Optional<Movie> {
        var movie = Movie("testMovie")
        movie.id=1
        return Optional.of(movie)
    }

    @Test
    fun updateMovie() {
        Mockito.`when`(movieRepo.findById(any())).thenReturn(getMovie())
        Mockito.`when`(screenRepo.findByName(any())).thenReturn(getScreen())
        var slotDTo = RequestSlotDTO(1,"2024-05-21","1200")
        var movieDTO = RequestMovieDTO(1,"TestName", listOf(slotDTo),"TestScreen")
        movieService.updateMovie(1, movieDTO )
    }
    @Test
    fun updateMovieFailiue() {
        Mockito.`when`(movieRepo.findById(any())).thenReturn(Optional.empty<Movie>())
        var slotDTo = RequestSlotDTO(1,"2024-05-21","1200")
        var movieDTO = RequestMovieDTO(1,"TestName", listOf(slotDTo),"TestScreen")
        assertThrows(InternalServerException::class.java, Executable { movieService.updateMovie(1,movieDTO)})
    }
    private fun getScreen(): Screen {
        var screen = Screen("TestScreen")
        screen.id =1
        return screen
    }
}