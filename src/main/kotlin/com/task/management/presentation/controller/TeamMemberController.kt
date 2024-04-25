package com.task.management.presentation.controller

import com.task.management.application.dto.TaskDTO
import com.task.management.application.dto.TeamMemberDTO
import com.task.management.infrastructure.persistence.entity.TeamMember
import com.task.management.exceptions.ResourceNotFoundException
import com.task.management.application.service.TeamMemberService
import com.task.management.presentation.dto.CreateTeamMemberRequestDTO
import com.task.management.presentation.dto.UpdateTeamMemberRequestDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/team-member")
class TeamMemberController(private val teamMemberService: TeamMemberService): BaseController() {
    @GetMapping
    fun getAllTeamMembers(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): Page<TeamMemberDTO> {
        val pageable = PageRequest.of(page, size)
        return teamMemberService.getAllTeamMembers(pageable)
    }

    @GetMapping("/{id}")
    fun getTeamMemberById(@PathVariable id: Long): ResponseEntity<TeamMemberDTO> {
        return ResponseEntity.ok(teamMemberService.getTeamMemberById(id))
    }

    @PostMapping("/")
    fun createTeamMember(@RequestBody teamMember: CreateTeamMemberRequestDTO): ResponseEntity<TeamMemberDTO> {
        val createdTeamMember = teamMemberService.createTeamMember(teamMember)
        return ResponseEntity(createdTeamMember, HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun updateTeamMember(@PathVariable id: Long, @RequestBody updatedTeamMember: UpdateTeamMemberRequestDTO): ResponseEntity<TeamMemberDTO> {
        val teamMember = teamMemberService.updateTeamMember(id, updatedTeamMember)
        return ResponseEntity.ok(teamMember)
    }

    @DeleteMapping("/{id}")
    fun deleteTeamMember(@PathVariable id: Long): ResponseEntity<Void> {
        teamMemberService.deleteTeamMember(id)
        return ResponseEntity.noContent().build()
    }
}