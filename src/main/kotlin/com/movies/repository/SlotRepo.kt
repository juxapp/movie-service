package com.movies.repository

import com.movies.entity.Slot
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.sql.Timestamp

interface SlotRepo: JpaRepository<Slot,Long> {
     @Query(value = "select s.* from slot s " +
             "left join screen sn on sn.id=s.screen_id "+
             "where ((:startDate is null) OR s.date >= :startDate) AND ((:screenType is null) OR sn.name = :screenType)", nativeQuery = true)
     fun findAllSlots(startDate: Timestamp?, screenType: String?): List<Slot>?

     fun findSlotByMovieId(id: Long): List<Slot>?
}