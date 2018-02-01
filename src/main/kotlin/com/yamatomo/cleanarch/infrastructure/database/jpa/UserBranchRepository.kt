package com.yamatomo.cleanarch.infrastructure.database.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

import com.yamatomo.cleanarch.infrastructure.database.jpa.UserBranch

@Repository("com.yamatomo.cleanarch.infrastructure.database.jpa.UserBranchRepository")
interface UserBranchRepository: JpaRepository<UserBranch, Long> {
}
