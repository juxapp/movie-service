package com.movies.entity

import jakarta.persistence.*
import lombok.Builder
import lombok.Data


@Entity
@Table(name = "movie")
@Data
@Builder
class Movie(
    @Column(name = "name")
    var name: String,
): BaseEntity()