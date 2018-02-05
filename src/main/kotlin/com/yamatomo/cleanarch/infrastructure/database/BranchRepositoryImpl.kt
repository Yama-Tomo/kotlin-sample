package com.yamatomo.cleanarch.infrastructure.database

import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.NoResultException
import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier

import com.yamatomo.cleanarch.domain.Branch
import com.yamatomo.cleanarch.domain.User
import com.yamatomo.cleanarch.usecase.BranchRepository
import com.yamatomo.cleanarch.infrastructure.database.jpa.BranchRepository as InfraBranchRepository
import com.yamatomo.cleanarch.infrastructure.database.jpa.UserBranchRepository as InfraUserBranchRepository
import com.yamatomo.cleanarch.infrastructure.database.jpa.Branch as InfraBranchEntity
import com.yamatomo.cleanarch.infrastructure.database.jpa.UserBranch as InfraUserBranchEntity

@Component("BranchRepositoryImpl")
class BranchRepositoryImpl @Autowired constructor(
    @Qualifier("com.yamatomo.cleanarch.infrastructure.database.jpa.BranchRepository") private val branchRepo: InfraBranchRepository,
    @Qualifier("com.yamatomo.cleanarch.infrastructure.database.jpa.UserBranchRepository") private val userBranchRepo: InfraUserBranchRepository
): BranchRepository {
    @PersistenceContext
    private lateinit var em: EntityManager

    override fun findById(id: Long): Branch? {
        return try {
            val branch = em.createQuery("SELECT b FROM Branch b LEFT JOIN FETCH b.users WHERE b.id = :id", InfraBranchEntity::class.java)
                    .setParameter("id", id)
                    .singleResult

            convertBranch(branch)
        } catch (e: NoResultException) {
            null
        }
    }

    override fun findAll(): List<Branch> {
        val branches = em.createQuery("SELECT DISTINCT b FROM Branch b LEFT JOIN FETCH b.users", InfraBranchEntity::class.java)
                         .resultList ?: return listOf()

        return branches.map { convertBranch(it) }
    }

    override fun save(branch: Branch): Branch {
        val savedBranch = branchRepo.save(InfraBranchEntity(branch.id, branch.name))

        return Branch(savedBranch.id, branch.name)
    }  

    override fun remove(id: Long) {
        branchRepo.delete(id)
    }

    override fun addUser(userId: Long, branchId: Long) {
		userBranchRepo.save(InfraUserBranchEntity(null, userId, branchId))
    }

    private fun convertBranch(branch: InfraBranchEntity): Branch {
        return Branch(branch.id, branch.name, branch.users.map { User(it.id, it.firstName, it.lastName) })
    }
}
