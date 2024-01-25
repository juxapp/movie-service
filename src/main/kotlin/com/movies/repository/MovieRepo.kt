package com.movies.repository

import com.movies.entity.Movie
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MovieRepo: JpaRepository<Movie,Long> {
}