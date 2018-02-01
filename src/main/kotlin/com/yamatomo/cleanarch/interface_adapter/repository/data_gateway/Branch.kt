package com.yamatomo.cleanarch.interface_adapter.repository.data_gateway

import com.yamatomo.cleanarch.domain.Branch as BranchEntity

interface Branch {
    fun findById(id: Long): BranchEntity?
    fun findAll(): List<BranchEntity>
    fun save(branch: BranchEntity): BranchEntity
    fun remove(id: Long)
    fun addUser(userId: Long, branchId: Long)
}
