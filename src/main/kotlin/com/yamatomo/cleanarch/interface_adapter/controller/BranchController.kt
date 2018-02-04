package com.yamatomo.cleanarch.interface_adapter.controller

import com.yamatomo.cleanarch.domain.Branch
import com.yamatomo.cleanarch.usecase.BranchInteractor
import com.yamatomo.cleanarch.usecase.UserInteractor
import com.yamatomo.cleanarch.usecase.context.Context
import com.yamatomo.cleanarch.interface_adapter.presenter.BranchPresenter
import com.yamatomo.cleanarch.interface_adapter.presenter.UserPresenter

class BranchController constructor(private val context: Context) {
    fun show(): BranchPresenter {
        val id      = context.params.getFirst("id")
        val useCase = BranchInteractor(context.container)
  
		return BranchPresenter(useCase.branchById(id?.toLongOrNull()))
    }

    fun lists(): List<BranchPresenter> {
        val useCase = BranchInteractor(context.container)
        return useCase.branches().map { BranchPresenter(it) }
    }

    fun add(): BranchPresenter {
        val name    = context.params.getFirst("name") ?: ""
        val useCase = BranchInteractor(context.container)

        return BranchPresenter(useCase.add(Branch(name)))
    }

    fun modify(): Branch {
        val id      = context.params.getFirst("id")
        val name    = context.params.getFirst("name") ?: ""
        val useCase = BranchInteractor(context.container)

        return useCase.modify(Branch(id?.toLongOrNull(), name))
    }

    fun remove() {
        val id      = context.params.getFirst("id")
        val useCase = BranchInteractor(context.container)

        return useCase.remove(id?.toLongOrNull())
    }

    fun addUser(): UserPresenter {
        val branchId = context.params.getFirst("id")
        val userId   = context.params.getFirst("user_id")
        val useCase  = BranchInteractor(context.container)

        useCase.addUser(userId?.toLong(), branchId?.toLong())

        return UserPresenter(UserInteractor(context.container).userById(userId?.toLongOrNull()))
    }
}
