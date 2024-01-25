package com.movies.entity

import jakarta.persistence.*
import java.sql.Timestamp

@Entity
@Table(name = "slot")
class Slot(
    @Column(name = "date")
    var date: Timestamp? = null,

    @Column(name = "time")
    var time: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    var movie: Movie? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "screen_id")
    var screen: Screen? = null
): BaseEntity()