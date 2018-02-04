package com.yamatomo.cleanarch.infrastructure.database

import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.NoResultException
import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier

import com.yamatomo.cleanarch.domain.User as UserEntity
import com.yamatomo.cleanarch.domain.Branch
import com.yamatomo.cleanarch.interface_adapter.repository.data_gateway.User as DataGatewayInterface
import com.yamatomo.cleanarch.infrastructure.database.jpa.UserRepository
import com.yamatomo.cleanarch.infrastructure.database.jpa.User as InfraUserEntity

@Component
class User @Autowired constructor(
    @Qualifier("com.yamatomo.cleanarch.infrastructure.database.jpa.UserRepository") private val repo: UserRepository
): DataGatewayInterface {
    @PersistenceContext
    private lateinit var em: EntityManager

    override fun findById(id: Long): UserEntity? {
        return try {
            val user = em.createQuery("SELECT m FROM User m LEFT JOIN FETCH m.branches WHERE m.id = :id", InfraUserEntity::class.java)
                    .setParameter("id", id)
                    .singleResult

            convertUserEntity(user)
        } catch (e: NoResultException) {
            null
        }
    }

    override fun findAll(): List<UserEntity> {
        // distinctをつけないと usersが重複してしまう http://d.hatena.ne.jp/taedium/20071222/p2
        val users = em.createQuery("SELECT DISTINCT m FROM User m LEFT JOIN FETCH m.branches", InfraUserEntity::class.java)
                      .resultList ?: return listOf()

        return users.map { convertUserEntity(it) }
    }

    override fun save(user: UserEntity): UserEntity {
        val savedUser = repo.save(InfraUserEntity(user.id, user.firstName, user.lastName))

        return UserEntity(savedUser.id, user.firstName, user.lastName, user.branches)
    }  

    override fun remove(id: Long) {
        repo.delete(id)
    }

    private fun convertUserEntity(user: InfraUserEntity): UserEntity {
        return UserEntity(user.id, user.firstName, user.lastName, user.branches.map { branch -> Branch(branch.id, branch.name) })
    }
}
