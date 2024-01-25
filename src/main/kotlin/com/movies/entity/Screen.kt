package com.movies.entity

import jakarta.persistence.*


@Entity
@Table(name = "screen")
class Screen (
    @Column(name = "name")
    var name: String? = null,
    ): BaseEntity()