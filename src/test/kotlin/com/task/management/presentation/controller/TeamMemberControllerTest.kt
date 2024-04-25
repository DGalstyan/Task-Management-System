package com.task.management.presentation.controller

import com.task.management.application.dto.TaskDTO
import com.task.management.application.dto.TeamMemberDTO
import com.task.management.infrastructure.persistence.entity.TeamMember
import com.task.management.exceptions.ResourceNotFoundException
import com.task.management.application.service.TeamMemberService
import com.task.management.enums.Priority
import com.task.management.enums.TaskStatus
import com.task.management.presentation.dto.CreateTeamMemberRequestDTO
import com.task.management.presentation.dto.UpdateTeamMemberRequestDTO
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
class TeamMemberControllerTest {

    @Mock
    lateinit var teamMemberService: TeamMemberService

    @InjectMocks
    lateinit var teamMemberController: TeamMemberController

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

        `when`(teamMemberService.getAllTeamMembers(pageable)).thenReturn(teamMembersPage)

        val result = teamMemberController.getAllTeamMembers(page, size)

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

        `when`(teamMemberService.getTeamMemberById(memberId)).thenReturn(teamMemberDTO)

        val result = teamMemberController.getTeamMemberById(memberId)

        assertThat(result).isEqualTo(ResponseEntity.ok(teamMemberDTO))
    }

    @Test
    fun createTeamMember() {
        val requestDTO = CreateTeamMemberRequestDTO(
            name = "John",
            email = "john@example.com"
        )

        val createdTeamMemberDTO = TeamMemberDTO(
            id = 1L,
            name = "John",
            email = "john@example.com"
        )

        `when`(teamMemberService.createTeamMember(requestDTO)).thenReturn(createdTeamMemberDTO)

        val result = teamMemberController.createTeamMember(requestDTO)

        assertThat(result).isEqualTo(ResponseEntity(createdTeamMemberDTO, HttpStatus.CREATED))
    }

    @Test
    fun updateTeamMember() {
        val memberId = 1L

        val requestDTO = UpdateTeamMemberRequestDTO(
            name = "John",
            email = "john@example.com"
        )

        val updatedTeamMemberDTO = TeamMemberDTO(
            id = 1L,
            name = "John",
            email = "john@example.com"
        )

        `when`(teamMemberService.updateTeamMember(memberId, requestDTO)).thenReturn(updatedTeamMemberDTO)

        val result = teamMemberController.updateTeamMember(memberId, requestDTO)

        assertThat(result).isEqualTo(ResponseEntity.ok(updatedTeamMemberDTO))
    }

    @Test
    fun deleteTeamMember() {
        val memberId = 1L

        val result = teamMemberController.deleteTeamMember(memberId)

        assertThat(result.statusCode).isEqualTo(HttpStatus.NO_CONTENT)
    }
}

