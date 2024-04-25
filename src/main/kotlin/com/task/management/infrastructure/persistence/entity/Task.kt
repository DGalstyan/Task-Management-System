package com.task.management.infrastructure.persistence.entity

import com.task.management.enums.Priority
import com.task.management.enums.TaskStatus
import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
data class Task(
    var title: String,
    var description: String,
    var startDate: LocalDateTime? = null,
    var completionDate: LocalDateTime? = null,
    @Enumerated(EnumType.STRING)
    var priority: Priority = Priority.LOW,
    @ManyToOne
    var assignedTo: TeamMember? = null,
    @Enumerated(EnumType.STRING)
    var status: TaskStatus = TaskStatus.TODO
) : BaseEntity() {
    // No-arg constructor for JPA
    constructor() : this("", "", null, null, Priority.LOW, null, TaskStatus.TODO)
}