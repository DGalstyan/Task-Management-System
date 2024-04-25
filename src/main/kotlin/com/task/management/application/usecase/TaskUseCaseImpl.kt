package com.task.management.application.usecase

import com.task.management.application.dto.TaskDTO
import com.task.management.application.dto.TeamMemberDTO
import com.task.management.application.mapper.TaskMapper
import com.task.management.domain.model.Report
import com.task.management.enums.Priority
import com.task.management.enums.TaskStatus
import com.task.management.exceptions.ResourceNotFoundException
import com.task.management.infrastructure.persistence.entity.Task
import com.task.management.infrastructure.persistence.entity.TeamMember
import com.task.management.infrastructure.persistence.repository.TaskRepository
import com.task.management.infrastructure.persistence.repository.TeamMemberRepository
import com.task.management.presentation.dto.CreateTaskRequestDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class TaskUseCaseImpl(
    private val taskRepository: TaskRepository,
    private val teamMemberRepository: TeamMemberRepository,
    private val taskMapper: TaskMapper
) : TaskUseCase {
    override fun createTask(requestDTO: CreateTaskRequestDTO): TaskDTO {
        val status= TaskStatus.valueOf(requestDTO.status.uppercase(Locale.ROOT))
        val task = Task(
            title = requestDTO.title,
            description = requestDTO.description,
            status = status,
            priority= Priority.LOW
        )
        return taskRepository.save(task).toDTO()
    }

    override fun updateTask(id: Long, updatedTaskDTO: TaskDTO): TaskDTO {
        val existingTask = taskRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Task", "id", id) }
            .apply {
                title = updatedTaskDTO.title
                description = updatedTaskDTO.description
                startDate = updatedTaskDTO.startDate
                completionDate = updatedTaskDTO.completionDate
                priority = updatedTaskDTO.priority
                status = updatedTaskDTO.status
            }
        return taskRepository.save(existingTask).toDTO()
    }

    override fun getAllTasks(pageable: Pageable): Page<TaskDTO> {
        val taskEntitiesPage = taskRepository.findAll(pageable)
        return taskEntitiesPage.map { it.toDTO() }
    }

    override fun getTaskById(id: Long): TaskDTO {
        return taskRepository.findById(id).orElseThrow { ResourceNotFoundException("Task not found", "id", id) }.toDTO()
    }

    override fun deleteTask(id: Long) {
        taskRepository.deleteById(id)
    }

    override fun assignTaskToMember(taskId: Long, memberId: Long): TaskDTO {
        val task = taskRepository.findById(taskId).orElseThrow { ResourceNotFoundException("Task not found", "id", taskId) }
        val member = teamMemberRepository.findById(memberId).orElseThrow { ResourceNotFoundException("Member ", "id", memberId) }
        task.assignedTo = member
        return taskRepository.save(task).toDTO()
    }

    override fun updateTaskStatus(taskId: Long, status: String): TaskDTO {
        val task = taskRepository.findById(taskId).orElseThrow { ResourceNotFoundException("Task not found", "id", taskId) }
        task.status = TaskStatus.valueOf(status.uppercase(Locale.ROOT))

        if (task.status == TaskStatus.IN_PROGRESS) {
            task.startDate = LocalDateTime.now()
        }

        return taskRepository.save(task).toDTO()
    }

    override fun markTaskAsComplete(taskId: Long): TaskDTO {
        val task = taskRepository.findById(taskId).orElseThrow { ResourceNotFoundException("Task not found", "id", taskId) }
        task.status = TaskStatus.DONE
        task.completionDate = LocalDateTime.now()
        return taskRepository.save(task).toDTO()
    }

    override fun generateReportByPeriod(periodStartDate: LocalDate, periodEndDate: LocalDate): Report {
        val completedTasks = taskRepository.findByCompletionDateBetween(periodStartDate.atStartOfDay(), periodEndDate.atStartOfDay())
        val numberOfTasksCompleted = completedTasks.size
        val totalDuration = completedTasks.mapNotNull { task ->
            task.startDate?.let { startDate ->
                task.completionDate?.let { completionDate ->
                    Duration.between(startDate, completionDate)
                }
            }
        }.sumOf { duration ->
            duration.toMinutes()
        }
        val averageDuration = if (numberOfTasksCompleted > 0) totalDuration/numberOfTasksCompleted else 0.0

        return Report(
            period = "Period $periodStartDate - $periodEndDate",
            numberOfTasksCompleted = numberOfTasksCompleted,
            averageTimeToCompleteTasks = averageDuration.toDouble()
        )
    }

    override fun generateReportByTeamMember(teamMemberId: Long): Report {
        val completedTasks = taskRepository.findByAssignedToIdAndStatus(teamMemberId, TaskStatus.DONE)
        val numberOfTasksCompleted = completedTasks.size
        val totalDuration = completedTasks.mapNotNull { task ->
            task.startDate?.let { startDate ->
                task.completionDate?.let { completionDate ->
                    Duration.between(startDate, completionDate)
                }
            }
        }.sumOf { duration ->
            duration.toMinutes()
        }
        val averageDuration = if (numberOfTasksCompleted > 0) totalDuration/numberOfTasksCompleted else 0.0


        return Report(
            period = "Team Member ID: $teamMemberId",
            numberOfTasksCompleted = numberOfTasksCompleted,
            averageTimeToCompleteTasks = averageDuration.toDouble()
        )
    }

    private fun Task.toDTO(): TaskDTO {
        return taskMapper.toDTO(this)
    }
}