package com.task.management.infrastructure.persistence.repository

import com.task.management.infrastructure.persistence.entity.Task
import com.task.management.enums.TaskStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface TaskRepository : JpaRepository<Task, Long> {
    fun findByCompletionDateBetween(startDate: LocalDateTime, endDate: LocalDateTime): List<Task>

    fun findByAssignedToIdAndStatus(memberId: Long, status: TaskStatus): List<Task>
}