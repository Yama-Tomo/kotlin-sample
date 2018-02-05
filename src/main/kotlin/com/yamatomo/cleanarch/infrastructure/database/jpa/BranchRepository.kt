package com.yamatomo.cleanarch.infrastructure.database.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository("com.yamatomo.cleanarch.infrastructure.database.jpa.BranchRepository")
interface BranchRepository: JpaRepository<Branch, Long> {
    fun findById(id: Long): Branch
}
