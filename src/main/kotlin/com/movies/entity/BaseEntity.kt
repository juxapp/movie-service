package com.movies.entity

import jakarta.persistence.*
import java.time.*


@MappedSuperclass
abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long? = 0

    @Column(updatable = false)
    open lateinit var createdAt: ZonedDateTime

    open lateinit var updatedAt: ZonedDateTime

    @PrePersist
    fun prePersist() {
        createdAt = ZonedDateTime.now(ZoneId.of("UTC"))
        updatedAt = ZonedDateTime.now(ZoneId.of("UTC"))
    }

    @PreUpdate
    fun preUpdate() {
        updatedAt = ZonedDateTime.now(ZoneId.of("UTC"))
    }
}
