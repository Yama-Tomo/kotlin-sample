package com.yamatomo.cleanarch.usecase.context

import com.yamatomo.cleanarch.usecase.BranchRepository
import com.yamatomo.cleanarch.usecase.UserRepository

interface DiContainer {
    fun getUserRepository(): UserRepository
    fun getBranchRepository(): BranchRepository
}