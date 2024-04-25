package com.task.management.application.usecase

import com.task.management.application.dto.TaskDTO
import com.task.management.application.dto.TeamMemberDTO
import com.task.management.domain.model.Report
import com.task.management.infrastructure.persistence.entity.Task
import com.task.management.infrastructure.persistence.entity.TeamMember
import com.task.management.presentation.dto.CreateTaskRequestDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.time.LocalDate

interface TaskUseCase {
    fun createTask(requestDTO: CreateTaskRequestDTO): TaskDTO

    fun updateTask(id: Long, updatedTaskDTO: TaskDTO): TaskDTO

    fun getAllTasks(pageable: Pageable): Page<TaskDTO>

    fun getTaskById(id: Long): TaskDTO

    fun deleteTask(id: Long)

    fun assignTaskToMember(taskId: Long, memberId: Long): TaskDTO

    fun updateTaskStatus(taskId: Long, status: String): TaskDTO

    fun markTaskAsComplete(taskId: Long): TaskDTO

    fun generateReportByPeriod(periodStartDate: LocalDate, periodEndDate: LocalDate): Report

    fun generateReportByTeamMember(teamMemberId: Long): Report
}