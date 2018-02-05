package com.yamatomo.cleanarch.infrastructure

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

import com.yamatomo.cleanarch.usecase.context.DiContainer
import com.yamatomo.cleanarch.usecase.UserRepository
import com.yamatomo.cleanarch.usecase.BranchRepository
import com.yamatomo.cleanarch.infrastructure.database.UserRepositoryImpl
import com.yamatomo.cleanarch.infrastructure.database.BranchRepositoryImpl

@Component
class DiContainerImpl @Autowired constructor(
    @Qualifier("BranchRepositoryImpl") private val branchRepository: BranchRepositoryImpl,
    @Qualifier("UserRepositoryImpl") private val userRepository: UserRepositoryImpl
): DiContainer {
    override fun getBranchRepository(): BranchRepository {
        return branchRepository
    }

    override fun getUserRepository(): UserRepository {
        return userRepository
    }
}