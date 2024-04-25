package com.task.management.config

import com.task.management.application.mapper.TaskMapper
import com.task.management.application.usecase.TaskUseCase
import com.task.management.application.usecase.TaskUseCaseImpl
import com.task.management.infrastructure.persistence.repository.TaskRepository
import com.task.management.infrastructure.persistence.repository.TeamMemberRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class UseCaseConfig {

    @Bean
    fun createTaskUseCaseImpl(taskRepository: TaskRepository, teamMemberRepository: TeamMemberRepository,taskMapper: TaskMapper): TaskUseCase {
        return TaskUseCaseImpl(taskRepository, teamMemberRepository, taskMapper)
    }
}