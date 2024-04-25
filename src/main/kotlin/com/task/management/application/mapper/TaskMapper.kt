package com.task.management.application.mapper

import com.task.management.application.dto.TaskDTO
import com.task.management.infrastructure.persistence.entity.Task
import org.springframework.stereotype.Component

@Component
class TaskMapper {
    fun toDTO(task: Task): TaskDTO {
        return TaskDTO(
            id = task.id,
            title = task.title,
            description = task.description,
            startDate = task.startDate,
            completionDate = task.completionDate,
            priority = task.priority,
            assignedTo = task.assignedTo,
            status = task.status
        )
    }
}