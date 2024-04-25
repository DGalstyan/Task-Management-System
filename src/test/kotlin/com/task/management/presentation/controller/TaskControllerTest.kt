package com.task.management.presentation.controller

import com.task.management.application.dto.TaskDTO
import com.task.management.application.service.TaskService
import com.task.management.domain.model.Report
import com.task.management.enums.Priority
import com.task.management.enums.TaskStatus
import com.task.management.presentation.dto.CreateTaskRequestDTO
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.time.LocalDate
import java.time.LocalDateTime


@ExtendWith(MockitoExtension::class)
class TaskControllerTest {

    @Mock
    lateinit var taskService: TaskService

    @InjectMocks
    lateinit var taskController: TaskController

    @Test
    fun createTask() {
        val requestDTO = CreateTaskRequestDTO(
            title = "Sample Task",
            description = "This is a sample task",
            status = "IN_PROGRESS"
        )
        val task =TaskDTO(
            id = 1L,
            title = "Sample Task",
            description = "This is a sample task",
            startDate = LocalDateTime.now(),
            completionDate = LocalDateTime.now().plusDays(1),
            priority = Priority.HIGH,
            assignedTo = null,
            status = TaskStatus.IN_PROGRESS
        )
        val expectedResponse = ResponseEntity(task, HttpStatus.CREATED)


        `when`(taskService.createTask(requestDTO)).thenReturn(task)

        val result = taskController.createTask(requestDTO)

        assertThat(result).isEqualTo(expectedResponse)
    }

    @Test
    fun getTaskById() {
        val taskId = 1L
        val task = TaskDTO(
            id = 1L,
            title = "Sample Task",
            description = "This is a sample task",
            startDate = LocalDateTime.now(),
            completionDate = LocalDateTime.now().plusDays(1),
            priority = Priority.HIGH,
            assignedTo = null,
            status = TaskStatus.IN_PROGRESS
        )
        val expectedResponse = ResponseEntity.ok(task)

        `when`(taskService.getTaskById(taskId)).thenReturn(task)


        val result = taskController.getTaskById(taskId)

        assertThat(result).isEqualTo(expectedResponse)
    }

    @Test
    fun updateTask() {
        // Mock updated task DTO
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
        val expectedResponse = ResponseEntity.ok(updatedTask)

        // Mock service method
        `when`(taskService.updateTask(taskId, updatedTask)).thenReturn(updatedTask)

        // Call controller method
        val result = taskController.updateTask(taskId, updatedTask)

        // Verify result
        assertThat(result).isEqualTo(expectedResponse)
    }

    @Test
    fun getAllTasks() {
        // Mock pageable parameters
        val page = 0
        val size = 10
        val pageable = PageRequest.of(page, size)

        // Mock tasks and page
        val taskDTOList = listOf(
            TaskDTO(
                id = 1L,
                title = "Sample Task",
                description = "This is a sample task",
                startDate = LocalDateTime.now(),
                completionDate = LocalDateTime.now().plusDays(1),
                priority = Priority.HIGH,
                assignedTo = null,
                status = TaskStatus.IN_PROGRESS
            ),
            TaskDTO(
                id = 2L,
                title = "Sample Task",
                description = "This is a sample task",
                startDate = LocalDateTime.now(),
                completionDate = LocalDateTime.now().plusDays(1),
                priority = Priority.HIGH,
                assignedTo = null,
                status = TaskStatus.IN_PROGRESS
            )
        )
        val pageRequest = PageRequest.of(0, 10) // Page number 0, page size 10

       // Create a Page<TaskDTO> object using PageImpl constructor
        val tasksPage: Page<TaskDTO> = PageImpl(taskDTOList, pageRequest, taskDTOList.size.toLong())

        val expectedResponse = ResponseEntity.ok(tasksPage)

        // Mock service method
        `when`(taskService.getAllTasks(pageable)).thenReturn(tasksPage)

        // Call controller method
        val result = taskController.getAllTasks(page, size)

        // Verify result
        assertThat(result).isEqualTo(expectedResponse)
    }

    @Test
    fun deleteTask() {
        // Mock task ID
        val taskId = 1L

        // Call controller method
        taskController.deleteTask(taskId)

        // Verify that service method was called with the correct task ID
        verify(taskService).deleteTask(taskId)
    }

    @Test
    fun assignTaskToMember() {
        val taskId = 1L
        val memberId = 1L


        val task = TaskDTO(
            id = 1L,
            title = "Sample Task",
            description = "This is a sample task",
            startDate = LocalDateTime.now(),
            completionDate = LocalDateTime.now().plusDays(1),
            priority = Priority.HIGH,
            assignedTo = null,
            status = TaskStatus.IN_PROGRESS
        )
        val expectedResponse = ResponseEntity(task, HttpStatus.ACCEPTED)

        `when`(taskService.assignTaskToMember(taskId, memberId)).thenReturn(task)

        val result = taskController.assignTaskToMember(taskId, memberId)

        assertThat(result).isEqualTo(expectedResponse)
    }

    @Test
    fun updateTaskStatus() {
        val taskId = 1L
        val status = "IN_PROGRESS"

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
        val expectedResponse = ResponseEntity(updatedTask, HttpStatus.ACCEPTED)

        `when`(taskService.updateTaskStatus(taskId, status)).thenReturn(updatedTask)

        val result = taskController.updateTaskStatus(taskId, status)

        assertThat(result).isEqualTo(expectedResponse)
    }

    @Test
    fun markTaskAsComplete() {
        // Mock task ID
        val taskId = 1L

        // Mock updated task and response DTO
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
        val expectedResponse = ResponseEntity(updatedTask, HttpStatus.ACCEPTED)

        // Mock service method
        `when`(taskService.markTaskAsComplete(taskId)).thenReturn(updatedTask)

        // Call controller method
        val result = taskController.markTaskAsComplete(taskId)

        // Verify result
        assertThat(result).isEqualTo(expectedResponse)
    }

    @Test
    fun generateReportByPeriod() {
        // Mock start and end dates
        val startDate = LocalDate.now()
        val endDate = LocalDate.now().plusDays(7)

        // Mock report and response entity
        val report = Report(
            period = "April 2024", // Example period
            numberOfTasksCompleted = 10, // Example number of completed tasks
            averageTimeToCompleteTasks = 5.7 // Example average time to complete tasks
        )
        val expectedResponse = ResponseEntity.ok(report)

        // Mock service method
        `when`(taskService.generateReportByPeriod(startDate, endDate)).thenReturn(report)

        // Call controller method
        val result = taskController.generateReportByPeriod(startDate, endDate)

        // Verify result
        assertThat(result).isEqualTo(expectedResponse)
    }

    @Test
    fun generateReportByTeamMember() {
        val memberId = 1L

        val report = Report(
            period = "April 2024",
            numberOfTasksCompleted = 10,
            averageTimeToCompleteTasks = 5.7
        )
        val expectedResponse = ResponseEntity.ok(report)

        // Mock service method
        `when`(taskService.generateReportByTeamMember(memberId)).thenReturn(report)

        // Call controller method
        val result = taskController.generateReportByTeamMember(memberId)

        // Verify result
        assertThat(result).isEqualTo(expectedResponse)
    }
}
