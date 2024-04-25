package com.task.management.infrastructure.persistence.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@MappedSuperclass
abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(updatable = false)
    @CreationTimestamp
    var createdAt: LocalDateTime? = null

    @UpdateTimestamp
    @LastModifiedDate
    var updatedAt: LocalDateTime? = null
}
