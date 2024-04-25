package com.task.management.application.mapper

import com.task.management.application.dto.TeamMemberDTO
import com.task.management.infrastructure.persistence.entity.TeamMember
import org.springframework.stereotype.Component

@Component
class TeamMemberMapper {
    fun toDTO(teamMember: TeamMember): TeamMemberDTO {
        return TeamMemberDTO(
            id = teamMember.id,
            name = teamMember.name,
            email = teamMember.email
        )
    }
}