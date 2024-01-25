package com.movies.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.movies.dto.JsonData
import com.movies.entity.Movie
import com.movies.entity.Screen
import com.movies.entity.Slot
import com.movies.exceptions.InternalServerException
import com.movies.repository.MovieRepo
import com.movies.repository.ScreenRepo
import com.movies.repository.SlotRepo
import jakarta.annotation.PostConstruct
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Component
import java.sql.Timestamp
import java.text.SimpleDateFormat


@Component
@Slf4j
class MovieComponent(
    private val objectMapper: ObjectMapper,
    private val movieRepo: MovieRepo,
    private val screenRepo: ScreenRepo,
    private val slotRepo: SlotRepo
) {

    @PostConstruct
    fun init() {
        loadJson()
    }

    fun loadJson() {
        try {
            val readText = MovieComponent::class.java.getResource("/movies.json").readText()
            val jsonString = "{\"data\":" + readText + "}"
            val jsonData = objectMapper.readValue(jsonString, JsonData::class.java)
            val movies = jsonData.data.map { it.name }.toSet()
            val screen = jsonData.data.map { it.screen }.toSet()
            val movieList = movies.map { Movie(it.toString()) }.toList()
            val screenList = screen.map { Screen(it.toString()) }.toList()
            movieRepo.saveAll(movieList)
            screenRepo.saveAll(screenList)
            val movieMap = movieList.associateBy { it.name }
            val screenMap = screenList.associateBy { it.name }

            val slotList = ArrayList<Slot>()
            jsonData.data.map { it ->
                it.slots.forEach { slot ->
                    val newSlot = Slot(
                        time = slot.time,
                        date = toTimeStamp(slot.date),
                        movie = movieMap[it.name],
                        screen = screenMap[it.screen]
                    )
                    slotList.add(newSlot)
                }
                slotRepo.saveAll(slotList)
            }
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

}