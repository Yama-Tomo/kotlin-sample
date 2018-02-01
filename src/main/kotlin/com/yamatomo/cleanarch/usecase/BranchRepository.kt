package com.yamatomo.cleanarch.usecase

import com.yamatomo.cleanarch.domain.Branch

interface BranchRepository {
    fun findById(id: Long): Branch?
    fun findAll(): List<Branch>
    fun save(user: Branch): Branch
    fun remove(id: Long)
    fun addUser(userId: Long, branchId: Long)
}
