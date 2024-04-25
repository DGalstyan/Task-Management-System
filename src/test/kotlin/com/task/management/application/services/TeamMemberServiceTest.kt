package com.task.management.application.services

import com.task.management.application.dto.TeamMemberDTO
import com.task.management.application.service.TeamMemberService
import com.task.management.presentation.dto.CreateTeamMemberRequestDTO
import com.task.management.presentation.dto.UpdateTeamMemberRequestDTO
import com.task.management.application.usecase.TeamMemberUseCase
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
import org.springframework.transaction.annotation.Transactional

@ExtendWith(MockitoExtension::class)
class TeamMemberServiceTest {

    @Mock
    lateinit var teamMemberUseCase: TeamMemberUseCase

    @InjectMocks
    lateinit var teamMemberService: TeamMemberService

    @Test
    fun getAllTeamMembers() {
        val page = 0
        val size = 10
        val pageable = PageRequest.of(page, size)

        val taskDTOList = listOf(
            TeamMemberDTO(
                id = 1L,
                name = "John",
                email = "john@example.com"
            ),
            TeamMemberDTO(
                id = 1L,
                name = "John",
                email = "john@example.com"
            )
        )
        val pageRequest = PageRequest.of(0, 10)

        val teamMembersPage: Page<TeamMemberDTO> = PageImpl(taskDTOList, pageRequest, taskDTOList.size.toLong())

        `when`(teamMemberUseCase.getAllTeamMembers(pageable)).thenReturn(teamMembersPage)

        val result = teamMemberService.getAllTeamMembers(pageable)

        assertThat(result).isEqualTo(teamMembersPage)
    }

    @Test
    fun getTeamMemberById() {
        val memberId = 1L

        val teamMemberDTO = TeamMemberDTO(
            id = 1L,
            name = "John",
            email = "john@example.com"
        )

        `when`(teamMemberUseCase.getTeamMemberById(memberId)).thenReturn(teamMemberDTO)

        val result = teamMemberService.getTeamMemberById(memberId)

        assertThat(result).isEqualTo(teamMemberDTO)
    }

    @Test
    fun deleteTeamMember() {
        val memberId = 1L
        teamMemberService.deleteTeamMember(memberId)
        verify(teamMemberUseCase).deleteTeamMember(memberId)
    }
}
