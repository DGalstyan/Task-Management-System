package com.task.management.application.service

import com.task.management.application.dto.TaskDTO
import com.task.management.application.dto.TeamMemberDTO
import com.task.management.application.usecase.TeamMemberUseCase
import com.task.management.infrastructure.persistence.entity.TeamMember
import com.task.management.exceptions.ResourceNotFoundException
import com.task.management.infrastructure.persistence.repository.TeamMemberRepository
import com.task.management.presentation.dto.CreateTeamMemberRequestDTO
import com.task.management.presentation.dto.UpdateTeamMemberRequestDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class TeamMemberService(private val teamMemberUseCase: TeamMemberUseCase) {
    fun getAllTeamMembers(pageable: Pageable): Page<TeamMemberDTO> {
        return teamMemberUseCase.getAllTeamMembers(pageable)
    }

    fun getTeamMemberById(id: Long): TeamMemberDTO {
        return teamMemberUseCase.getTeamMemberById(id)
    }

    @Transactional
    fun createTeamMember(teamMember: CreateTeamMemberRequestDTO): TeamMemberDTO {
        return teamMemberUseCase.createTeamMember(teamMember)
    }

    @Transactional
    fun updateTeamMember(id: Long, updatedTeamMember: UpdateTeamMemberRequestDTO): TeamMemberDTO {
        return teamMemberUseCase.updateTeamMember(id, updatedTeamMember)
    }

    @Transactional
    fun deleteTeamMember(id: Long) {
        teamMemberUseCase.deleteTeamMember(id)
    }

    private fun TeamMember.toDTO(): TeamMemberDTO {
        return TeamMemberDTO(id, name, email)
    }
}