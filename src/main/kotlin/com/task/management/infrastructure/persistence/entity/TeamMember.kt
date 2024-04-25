package com.task.management.infrastructure.persistence.entity

import jakarta.persistence.Entity

@Entity
data class TeamMember(
    var name: String,
    var email: String
) : BaseEntity() {
    // No-arg constructor for JPA
    constructor() : this("", "")
}