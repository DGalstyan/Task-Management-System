package com.task.management.application.usecase

import com.task.management.application.dto.TaskDTO
import com.task.management.application.dto.TeamMemberDTO
import com.task.management.application.mapper.TaskMapper
import com.task.management.application.mapper.TeamMemberMapper
import com.task.management.exceptions.ResourceNotFoundException
import com.task.management.infrastructure.persistence.entity.Task
import com.task.management.infrastructure.persistence.entity.TeamMember
import com.task.management.infrastructure.persistence.repository.TeamMemberRepository
import com.task.management.presentation.dto.CreateTeamMemberRequestDTO
import com.task.management.presentation.dto.UpdateTeamMemberRequestDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

@Component
class TeamMemberUseCaseImpl(private val teamMemberRepository: TeamMemberRepository,
                            private val teamMemberMapper: TeamMemberMapper
) : TeamMemberUseCase {

    override fun createTeamMember(teamMember: CreateTeamMemberRequestDTO): TeamMemberDTO {
        return teamMemberRepository.save(TeamMember(name = teamMember.name, email = teamMember.email)).toDTO()
    }

    override fun updateTeamMember(id: Long, updatedTeamMember: UpdateTeamMemberRequestDTO): TeamMemberDTO {
        val existingTeamMember = teamMemberRepository.findById(id).orElseThrow { ResourceNotFoundException("Team member not found", "id", id)  }
        existingTeamMember.name = updatedTeamMember.name
        existingTeamMember.email = updatedTeamMember.email
        return teamMemberRepository.save(existingTeamMember).toDTO()
    }

    override fun deleteTeamMember(id: Long) {
        teamMemberRepository.deleteById(id)
    }

    override fun getTeamMemberById(id: Long): TeamMemberDTO {
        return teamMemberRepository.findById(id).orElseThrow { ResourceNotFoundException("Team Member", "id", id) }.toDTO()
    }

    override fun getAllTeamMembers(pageable: Pageable): Page<TeamMemberDTO> {
        val teamEntitiesPage = teamMemberRepository.findAll(pageable)
        return teamEntitiesPage.map { it.toDTO() }
    }

    private fun TeamMember.toDTO(): TeamMemberDTO {
        return teamMemberMapper.toDTO(this)
    }
}