package com.yamatomo.cleanarch.infrastructure.database

import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier

import com.yamatomo.cleanarch.domain.User as UserEntity
import com.yamatomo.cleanarch.interface_adpter.repository.data_gateway.User as DataGatewayInterface
import com.yamatomo.cleanarch.infrastructure.database.jpa.UserRepository
import com.yamatomo.cleanarch.infrastructure.database.jpa.User as InfraUserEntity

@Component
class User @Autowired constructor(
    @Qualifier("com.yamatomo.cleanarch.infrastructure.database.jpa.UserRepository") private val repo: UserRepository
): DataGatewayInterface {
    override fun findById(id: Long): UserEntity? {
        val user = repo.findById(id)
        if (user != null) {
            return UserEntity(user.id, user.firstName, user.lastName)
        }

        return null
    }

    override fun findAll(): List<UserEntity> {
        return repo.findAll().map { row -> UserEntity(row.id, row.firstName, row.lastName) }
    }

    override fun save(user: UserEntity): UserEntity {
        val savedUser = repo.save(InfraUserEntity(user.id, user.firstName, user.lastName))

		return UserEntity(savedUser.id, user.firstName, user.lastName)
    }  

    override fun remove(id: Long) {
		repo.delete(id)
	}
}
