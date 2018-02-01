package com.yamatomo.cleanarch.interface_adapter.repository

import com.yamatomo.cleanarch.domain.User as UserEntity
import com.yamatomo.cleanarch.usecase.UserRepository as UserRepositoryInterface
import com.yamatomo.cleanarch.interface_adapter.repository.data_gateway.User as DataGateway

class UserRepository constructor(private val gateway: DataGateway): UserRepositoryInterface {
    override fun findById(id: Long): UserEntity? {
        return gateway.findById(id)
    }
  
    override fun findAll(): List<UserEntity> {
        return gateway.findAll()
    }
  
    override fun save(user: UserEntity): UserEntity {
        return gateway.save(user)
    }

    override fun remove(id: Long) {
        return gateway.remove(id)
    }
}
