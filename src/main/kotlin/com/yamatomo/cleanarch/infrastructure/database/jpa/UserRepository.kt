package com.yamatomo.cleanarch.infrastructure.database.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

import com.yamatomo.cleanarch.infrastructure.database.jpa.User

@Repository("com.yamatomo.cleanarch.infrastructure.database.jpa.UserRepository")
interface UserRepository: JpaRepository<User, Long> {
    fun findById(id: Long): User
}
