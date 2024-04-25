package com.task.management.application.usecase

import com.task.management.application.dto.TaskDTO
import com.task.management.application.dto.TeamMemberDTO
import com.task.management.infrastructure.persistence.entity.Task
import com.task.management.infrastructure.persistence.entity.TeamMember
import com.task.management.presentation.dto.CreateTeamMemberRequestDTO
import com.task.management.presentation.dto.UpdateTeamMemberRequestDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface TeamMemberUseCase {
    fun createTeamMember(teamMember: CreateTeamMemberRequestDTO): TeamMemberDTO
    fun updateTeamMember(id: Long, updatedTeamMember: UpdateTeamMemberRequestDTO): TeamMemberDTO
    fun deleteTeamMember(id: Long)
    fun getTeamMemberById(id: Long): TeamMemberDTO

    fun getAllTeamMembers(pageable: Pageable): Page<TeamMemberDTO>
}