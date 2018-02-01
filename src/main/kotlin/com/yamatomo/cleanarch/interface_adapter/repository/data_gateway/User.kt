package com.yamatomo.cleanarch.interface_adapter.repository.data_gateway

import com.yamatomo.cleanarch.domain.User as UserEntity

interface User {
    fun findById(id: Long): UserEntity?
    fun findAll(): List<UserEntity>
    fun save(user: UserEntity): UserEntity
    fun remove(id: Long)
}
