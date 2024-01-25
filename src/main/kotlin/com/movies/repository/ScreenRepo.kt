package com.movies.repository

import com.movies.entity.Screen
import org.springframework.data.jpa.repository.JpaRepository

interface ScreenRepo: JpaRepository<Screen, Long> {
     fun findByName(screen: String?): Screen?
}