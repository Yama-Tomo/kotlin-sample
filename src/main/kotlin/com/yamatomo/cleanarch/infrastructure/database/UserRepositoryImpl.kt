package com.yamatomo.cleanarch.infrastructure.database

import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.NoResultException
import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier

import com.yamatomo.cleanarch.domain.User
import com.yamatomo.cleanarch.domain.Branch
import com.yamatomo.cleanarch.usecase.UserRepository
import com.yamatomo.cleanarch.infrastructure.database.jpa.UserRepository as InfraUserRepository
import com.yamatomo.cleanarch.infrastructure.database.jpa.User as InfraUserEntity

@Component("UserRepositoryImpl")
class UserRepositoryImpl @Autowired constructor(
    @Qualifier("com.yamatomo.cleanarch.infrastructure.database.jpa.UserRepository") private val repo: InfraUserRepository
): UserRepository {
    @PersistenceContext
    private lateinit var em: EntityManager

    override fun findById(id: Long): User? {
        return try {
            val user = em.createQuery("SELECT m FROM User m LEFT JOIN FETCH m.branches WHERE m.id = :id", InfraUserEntity::class.java)
                    .setParameter("id", id)
                    .singleResult

            convertUser(user)
        } catch (e: NoResultException) {
            null
        }
    }

    override fun findAll(): List<User> {
        // distinctをつけないと usersが重複してしまう http://d.hatena.ne.jp/taedium/20071222/p2
        val users = em.createQuery("SELECT DISTINCT m FROM User m LEFT JOIN FETCH m.branches", InfraUserEntity::class.java)
                      .resultList ?: return listOf()

        return users.map { convertUser(it) }
    }

    override fun save(user: User): User {
        val savedUser = repo.save(InfraUserEntity(user.id, user.firstName, user.lastName))

        return User(savedUser.id, user.firstName, user.lastName, user.branches)
    }  

    override fun remove(id: Long) {
        repo.delete(id)
    }

    private fun convertUser(user: InfraUserEntity): User {
        return User(user.id, user.firstName, user.lastName, user.branches.map { branch -> Branch(branch.id, branch.name) })
    }
}
