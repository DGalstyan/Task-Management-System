package com.task.management.presentation.controller

import com.task.management.application.dto.TaskDTO
import com.task.management.domain.model.Report
import com.task.management.infrastructure.persistence.entity.Task
import com.task.management.exceptions.ResourceNotFoundException
import com.task.management.application.service.TaskService
import com.task.management.application.usecase.TaskUseCase
import com.task.management.presentation.dto.CreateTaskRequestDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/tasks")
class TaskController(
    private val taskService: TaskService
) : BaseController() {

    @PostMapping
    fun createTask(@RequestBody requestDTO: CreateTaskRequestDTO): ResponseEntity<TaskDTO> {
        val task = taskService.createTask(requestDTO)
        return ResponseEntity(task, HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun getTaskById(@PathVariable id: Long): ResponseEntity<TaskDTO> {
        return ResponseEntity.ok(taskService.getTaskById(id))
    }

    @PutMapping("/{id}")
    fun updateTask(@PathVariable id: Long, @RequestBody updatedTask: TaskDTO): ResponseEntity<TaskDTO> {
        return ResponseEntity.ok(taskService.updateTask(id, updatedTask))
    }

    @GetMapping
    fun getAllTasks(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<Page<TaskDTO>>{
        val pageable = PageRequest.of(page, size)
        return ResponseEntity.ok(taskService.getAllTasks(pageable))
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteTask(@PathVariable id: Long) {
        taskService.deleteTask(id)
    }

    @PutMapping("/{taskId}/assign/{memberId}")
    fun assignTaskToMember(
        @PathVariable taskId: Long,
        @PathVariable memberId: Long
    ): ResponseEntity<TaskDTO> {
        return ResponseEntity(taskService.assignTaskToMember(taskId, memberId), HttpStatus.ACCEPTED)
    }

    @PutMapping("/{taskId}/{status}")
    fun updateTaskStatus(
        @PathVariable taskId: Long,
        @PathVariable status: String
    ): ResponseEntity<TaskDTO> {
        return ResponseEntity(taskService.updateTaskStatus(taskId, status), HttpStatus.ACCEPTED)
    }

    @PutMapping("/{taskId}/complete")
    fun markTaskAsComplete(
        @PathVariable taskId: Long
    ): ResponseEntity<TaskDTO> {
        return ResponseEntity(taskService.markTaskAsComplete(taskId), HttpStatus.ACCEPTED)
    }

    @GetMapping("/report/period")
    fun generateReportByPeriod(
        @RequestParam startDate: LocalDate,
        @RequestParam endDate: LocalDate
    ): ResponseEntity<Report> {
        return ResponseEntity.ok(taskService.generateReportByPeriod(startDate, endDate))
    }

    @GetMapping("/report/team-member/{memberId}")
    fun generateReportByTeamMember(
        @PathVariable memberId: Long
    ): ResponseEntity<Report> {
        return ResponseEntity.ok(taskService.generateReportByTeamMember(memberId))
    }
}