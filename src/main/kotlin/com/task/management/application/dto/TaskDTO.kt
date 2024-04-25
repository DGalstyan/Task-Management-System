package com.task.management.application.dto

import com.task.management.enums.Priority
import com.task.management.enums.TaskStatus
import com.task.management.infrastructure.persistence.entity.TeamMember
import java.time.LocalDateTime

data class TaskDTO(
    val id: Long?,
    val title: String,
    val description: String,
    val startDate: LocalDateTime?,
    val completionDate: LocalDateTime?,
    val priority: Priority,
    var assignedTo: TeamMember?,
    val status: TaskStatus
)