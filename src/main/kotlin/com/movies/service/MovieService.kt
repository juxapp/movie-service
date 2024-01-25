package com.movies.service


import com.movies.dto.MovieDTO
import com.movies.dto.RequestMovieDTO
import com.movies.dto.SlotDTO
import com.movies.entity.Slot
import com.movies.exceptions.InternalServerException
import com.movies.exceptions.ResourceNotFoundException
import com.movies.repository.MovieRepo
import com.movies.repository.ScreenRepo
import com.movies.repository.SlotRepo
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Service
class MovieService(
    var movieRepo: MovieRepo, var slotRepo: SlotRepo, var screenRepo: ScreenRepo

) {
    @Cacheable("moviesCache")
    fun getMovies(startDate: String?, screenType: String?): List<MovieDTO>? {
        try {
            val slotList = slotRepo.findAllSlots(toTimeStamp(startDate), screenType)
            val movieList = ArrayList<MovieDTO>()
            if (slotList != null) {
                slotList.groupBy { Pair(it.movie, it.screen) }.forEach {
                    val movieDTO = MovieDTO()
                    movieDTO.name = it.key.first?.name
                    movieDTO.screen = it.key.second?.name
                    movieDTO.slots = it.value.map { it->SlotDTO(time = it.time, date =  timeStampToDate(it.date)) }.toList()
                    movieList.add(movieDTO)
                }
            }
            return movieList
        } catch (ex: Exception) {
            throw InternalServerException(ex.message.toString())
        }

    }

    fun toTimeStamp(date: String?): Timestamp? {
        try {

            if (!date.isNullOrEmpty()) {
                val dateFormat = SimpleDateFormat("yyyy-MM-dd")
                return Timestamp(dateFormat.parse(date).time)
            }
        } catch (ex: Exception) {
            return null
        }
        return null
    }

    fun timeStampToDate(date: Timestamp?): String? {
        try {
          if(date!= null){
              val localDateTime: LocalDateTime = date.toLocalDateTime()
              val format = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
              return format
          }

        } catch (ex: Exception) {
            return null
        }
        return null
    }

    @Cacheable("moviesByIdCache")
    fun getMovieById(movieId: Long): List<MovieDTO> {
        try {
            val optionalMovie = movieRepo.findById(movieId)
            if (optionalMovie.isPresent) {
                val slotList = slotRepo.findSlotByMovieId(movieId)
                val movieList = ArrayList<MovieDTO>()
                slotList?.groupBy { Pair(it.movie, it.screen) }?.forEach {
                    val movieDTO = MovieDTO()
                    movieDTO.name = it.key.first?.name
                    movieDTO.screen = it.key.second?.name
                    movieDTO.slots = it.value.map { SlotDTO(time = it.time, date =  timeStampToDate(it.date)) }.toList()
                    movieList.add(movieDTO)
                }
                return movieList
            } else throw ResourceNotFoundException("Movie Not Found")
        } catch (ex: Exception) {
            throw InternalServerException(ex.message.toString())
        }
    }

    fun updateMovie(movieId: Long, movieDTO: RequestMovieDTO) {
        try{
            val optionalMovie = movieRepo.findById(movieId)
            if (optionalMovie.isPresent) {
                val movie = optionalMovie.get()
                movie.name = movieDTO.name ?: movie.name
                movieRepo.save(movie)
                val screen = screenRepo.findByName(movieDTO.screen)
                movieDTO.slots?.forEach {
                    val slot = Slot(date = toTimeStamp(it.date), time = it.time, movie = movie, screen = screen)
                    slot.id = it.id ?: 0
                    slotRepo.save(slot)
                }
            } else throw ResourceNotFoundException("Movie Not Found whith this id .")
        } catch (ex: Exception) {
            throw InternalServerException(ex.message.toString())
        }
    }
}


