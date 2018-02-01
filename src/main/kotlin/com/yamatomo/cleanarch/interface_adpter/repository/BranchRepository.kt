package com.yamatomo.cleanarch.interface_adpter.repository

import com.yamatomo.cleanarch.domain.Branch as BranchEntity
import com.yamatomo.cleanarch.usecase.BranchRepository as BranchRepositoryInterface
import com.yamatomo.cleanarch.interface_adpter.repository.data_gateway.Branch as DataGateway

class BranchRepository constructor(private val gateway: DataGateway): BranchRepositoryInterface {
    override fun findById(id: Long): BranchEntity? {
        return gateway.findById(id)
    }
  
    override fun findAll(): List<BranchEntity> {
        return gateway.findAll()
    }
  
    override fun save(user: BranchEntity): BranchEntity {
        return gateway.save(user)
    }

    override fun remove(id: Long) {
        gateway.remove(id)
    }

    override fun addUser(userId: Long, branchId: Long) {
        gateway.addUser(userId, branchId)
    }
}
