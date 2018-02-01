package com.yamatomo.cleanarch.infrastructure.database

import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.NoResultException
import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier

import com.yamatomo.cleanarch.domain.Branch as BranchEntity
import com.yamatomo.cleanarch.domain.User
import com.yamatomo.cleanarch.interface_adapter.repository.data_gateway.Branch as DataGatewayInterface
import com.yamatomo.cleanarch.infrastructure.database.jpa.BranchRepository
import com.yamatomo.cleanarch.infrastructure.database.jpa.UserBranchRepository
import com.yamatomo.cleanarch.infrastructure.database.jpa.Branch as InfraBranchEntity
import com.yamatomo.cleanarch.infrastructure.database.jpa.UserBranch as InfraUserBranchEntity

@Component
class Branch @Autowired constructor(
    @Qualifier("com.yamatomo.cleanarch.infrastructure.database.jpa.BranchRepository") private val branchRepo: BranchRepository,
    @Qualifier("com.yamatomo.cleanarch.infrastructure.database.jpa.UserBranchRepository") private val userBranchRepo: UserBranchRepository
): DataGatewayInterface {
    @PersistenceContext
    private lateinit var em: EntityManager

    override fun findById(id: Long): BranchEntity? {
        try {
            val branch = em.createQuery("SELECT b FROM Branch b LEFT JOIN FETCH b.users WHERE b.id = :id", InfraBranchEntity::class.java)
                         .setParameter("id", id)
                         .getSingleResult()

            return convertBranchEntity(branch)
        } catch (e: NoResultException) {
            return null
        }
    }

    override fun findAll(): List<BranchEntity> {
        val branches = em.createQuery("SELECT DISTINCT b FROM Branch b LEFT JOIN FETCH b.users", InfraBranchEntity::class.java)
                         .getResultList() ?: return listOf()

        return branches.map { convertBranchEntity(it) }
    }

    override fun save(branch: BranchEntity): BranchEntity {
        val savedBranch = branchRepo.save(InfraBranchEntity(branch.id, branch.name))

        return BranchEntity(savedBranch.id, branch.name)
    }  

    override fun remove(id: Long) {
        branchRepo.delete(id)
    }

    override fun addUser(userId: Long, branchId: Long) {
		userBranchRepo.save(InfraUserBranchEntity(null, userId, branchId))
    }

    private fun convertBranchEntity(branch: InfraBranchEntity): BranchEntity {
        return BranchEntity(branch.id, branch.name, branch.users.map { User(it.id, it.firstName, it.lastName) })
    }
}
