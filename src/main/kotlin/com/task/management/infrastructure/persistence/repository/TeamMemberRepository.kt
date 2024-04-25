package com.task.management.infrastructure.persistence.repository

import com.task.management.infrastructure.persistence.entity.TeamMember
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TeamMemberRepository : JpaRepository<TeamMember, Long>
