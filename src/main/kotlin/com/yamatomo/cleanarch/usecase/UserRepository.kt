package com.yamatomo.cleanarch.usecase

import com.yamatomo.cleanarch.domain.User

interface UserRepository {
    fun findById(id: Long): User?
    fun findAll(): List<User>
    fun save(user: User): User
    fun remove(id: Long)
}
