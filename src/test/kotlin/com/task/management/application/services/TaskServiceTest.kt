package com.task.management.application.services

import com.task.management.application.dto.TaskDTO
import com.task.management.application.service.TaskService
import com.task.management.application.usecase.TaskUseCase
import com.task.management.domain.model.Report
import com.task.management.enums.Priority
import com.task.management.enums.TaskStatus
import com.task.management.presentation.dto.CreateTaskRequestDTO
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.web.server.NotAcceptableStatusException
import java.time.LocalDate
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
class TaskServiceTest {

    @Mock
    lateinit var taskUseCase: TaskUseCase

    @InjectMocks
    lateinit var taskService: TaskService

    @Test
    fun createTask() {

        val requestDTO = CreateTaskRequestDTO(
            title = "Sample Task", description = "This is a sample task", status = "IN_PROGRESS"
        )

        val createdTaskDTO = TaskDTO(
            id = 1L,
            title = "Sample Task",
            description = "This is a sample task",
            startDate = LocalDateTime.now(),
            completionDate = LocalDateTime.now().plusDays(1),
            priority = Priority.HIGH,
            assignedTo = null,
            status = TaskStatus.IN_PROGRESS
        )

        `when`(taskUseCase.createTask(requestDTO)).thenReturn(createdTaskDTO)


        val result = taskService.createTask(requestDTO)
        assertThat(result).isEqualTo(createdTaskDTO)
    }

    @Test
    fun updateTask() {
        val taskId = 1L

        val updatedTask = TaskDTO(
            id = 1L,
            title = "Sample Task",
            description = "This is a sample task",
            startDate = LocalDateTime.now(),
            completionDate = LocalDateTime.now().plusDays(1),
            priority = Priority.HIGH,
            assignedTo = null,
            status = TaskStatus.IN_PROGRESS
        )

        `when`(taskUseCase.updateTask(taskId, updatedTask)).thenReturn(updatedTask)

        val result = taskService.updateTask(taskId, updatedTask)

        assertThat(result).isEqualTo(updatedTask)
    }

    @Test
    fun deleteTask() {
        val taskId = 1L
        taskService.deleteTask(taskId)
        verify(taskUseCase).deleteTask(taskId)
    }

    @Test
    fun assignTaskToMember() {
        val taskId = 1L
        val memberId = 1L

        val assignedTaskDTO = TaskDTO(
            id = 1L,
            title = "Sample Task",
            description = "This is a sample task",
            startDate = LocalDateTime.now(),
            completionDate = LocalDateTime.now().plusDays(1),
            priority = Priority.HIGH,
            assignedTo = null,
            status = TaskStatus.IN_PROGRESS
        )

        `when`(taskUseCase.assignTaskToMember(taskId, memberId)).thenReturn(assignedTaskDTO)

        val result = taskService.assignTaskToMember(taskId, memberId)

        assertThat(result).isEqualTo(assignedTaskDTO)
    }

    @Test
    fun updateTaskStatusWithUnassignedTask() {
        val taskId = 1L

        val taskDTO = TaskDTO(
            id = 1L,
            title = "Sample Task",
            description = "This is a sample task",
            startDate = LocalDateTime.now(),
            completionDate = LocalDateTime.now().plusDays(1),
            priority = Priority.HIGH,
            assignedTo = null,
            status = TaskStatus.IN_PROGRESS
        )

        `when`(taskUseCase.getTaskById(taskId)).thenReturn(taskDTO)

        assertThrows<NotAcceptableStatusException> {
            taskService.updateTaskStatus(taskId, "IN_PROGRESS")
        }
    }

    @Test
    fun markTaskAsComplete() {
        val taskId = 1L

        val completedTaskDTO = TaskDTO(
            id = 1L,
            title = "Sample Task",
            description = "This is a sample task",
            startDate = LocalDateTime.now(),
            completionDate = LocalDateTime.now().plusDays(1),
            priority = Priority.HIGH,
            assignedTo = null,
            status = TaskStatus.IN_PROGRESS
        )

        `when`(taskUseCase.markTaskAsComplete(taskId)).thenReturn(completedTaskDTO)

        val result = taskService.markTaskAsComplete(taskId)

        assertThat(result).isEqualTo(completedTaskDTO)
    }

    @Test
    fun generateReportByPeriod() {
        val startDate = LocalDate.now()
        val endDate = LocalDate.now().plusDays(7)

        val report = Report(
            period = "April 2024", numberOfTasksCompleted = 10, averageTimeToCompleteTasks = 5.7
        )

        `when`(taskUseCase.generateReportByPeriod(startDate, endDate)).thenReturn(report)

        val result = taskService.generateReportByPeriod(startDate, endDate)

        assertThat(result).isEqualTo(report)
    }

    @Test
    fun generateReportByTeamMember() {
        val memberId = 1L

        val report = Report(
            period = "April 2024", numberOfTasksCompleted = 10, averageTimeToCompleteTasks = 5.7
        )

        `when`(taskUseCase.generateReportByTeamMember(memberId)).thenReturn(report)

        val result = taskService.generateReportByTeamMember(memberId)

        assertThat(result).isEqualTo(report)
    }
}