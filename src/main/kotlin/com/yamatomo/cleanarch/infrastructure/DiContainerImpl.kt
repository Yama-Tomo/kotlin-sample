package com.yamatomo.cleanarch.infrastructure

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import com.yamatomo.cleanarch.usecase.context.DiContainer
import com.yamatomo.cleanarch.interface_adapter.repository.UserRepository
import com.yamatomo.cleanarch.interface_adapter.repository.BranchRepository
import com.yamatomo.cleanarch.infrastructure.database.User as UserDataGateway
import com.yamatomo.cleanarch.infrastructure.database.Branch as BranchDataGateway

@Component
class DiContainerImpl @Autowired constructor(
    private val branchDataGateway: BranchDataGateway,
    private val userDataGateway: UserDataGateway
): DiContainer {
    override fun getBranchRepository(): BranchRepository {
        return BranchRepository(branchDataGateway)
    }

    override fun getUserRepository(): UserRepository {
        return UserRepository(userDataGateway)
    }
}