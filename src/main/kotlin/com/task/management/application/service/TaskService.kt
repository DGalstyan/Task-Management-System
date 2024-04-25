package com.task.management.application.service

import com.task.management.application.dto.TaskDTO
import com.task.management.application.usecase.TaskUseCase
import com.task.management.domain.model.Report
import com.task.management.presentation.dto.CreateTaskRequestDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.NotAcceptableStatusException
import java.time.LocalDate

@Service
class TaskService(private val taskUseCase: TaskUseCase) {

    fun getAllTasks(pageable: Pageable): Page<TaskDTO> {
        return taskUseCase.getAllTasks(pageable)
    }

    fun getTaskById(id: Long): TaskDTO {
        return taskUseCase.getTaskById(id)
    }

    fun createTask(requestDTO: CreateTaskRequestDTO): TaskDTO {
        return taskUseCase.createTask(requestDTO)
    }

    @Transactional
    fun updateTask(id: Long, updatedTaskDTO: TaskDTO): TaskDTO {
        return taskUseCase.updateTask(id, updatedTaskDTO)
    }

    fun deleteTask(id: Long) {
        taskUseCase.deleteTask(id)
    }

    @Transactional
    fun assignTaskToMember(taskId: Long, memberId: Long): TaskDTO {
        return taskUseCase.assignTaskToMember(taskId, memberId)
    }

    @Transactional
    fun updateTaskStatus(taskId: Long, status: String): TaskDTO {
        val task = taskUseCase.getTaskById(taskId)
        requireNotNull(task.assignedTo) { throw NotAcceptableStatusException("The task must be assigned to a member before updating its status") }

        return taskUseCase.updateTaskStatus(taskId, status)
    }

    @Transactional
    fun markTaskAsComplete(taskId: Long): TaskDTO {
        return taskUseCase.markTaskAsComplete(taskId)
    }

    fun generateReportByPeriod(periodStartDate: LocalDate, periodEndDate: LocalDate): Report {
        return taskUseCase.generateReportByPeriod(periodStartDate, periodEndDate)
    }

    fun generateReportByTeamMember(teamMemberId: Long): Report {
        return taskUseCase.generateReportByTeamMember(teamMemberId)
    }
}