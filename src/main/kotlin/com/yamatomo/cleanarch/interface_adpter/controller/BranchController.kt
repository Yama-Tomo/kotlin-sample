package com.yamatomo.cleanarch.interface_adpter.controller

import com.yamatomo.cleanarch.domain.Branch
import com.yamatomo.cleanarch.usecase.exception.*
import com.yamatomo.cleanarch.usecase.BranchInteractor
import com.yamatomo.cleanarch.usecase.UserInteractor
import com.yamatomo.cleanarch.usecase.context.Context
import com.yamatomo.cleanarch.interface_adpter.presenter.BranchPresenter
import com.yamatomo.cleanarch.interface_adpter.presenter.UserPresenter
import com.yamatomo.cleanarch.interface_adpter.repository.UserRepository

class BranchController constructor(private val usecase: BranchInteractor) {
    fun show(context: Context): BranchPresenter {
        val id = context.params.getFirst("id")
  
		return BranchPresenter(usecase.branchById(id?.toLongOrNull()))
    }

    fun lists(): List<BranchPresenter> {
        return usecase.branches().map { BranchPresenter(it) }
    }

    fun add(context: Context): BranchPresenter {
        val name = context.params.getFirst("name") ?: ""

        return BranchPresenter(usecase.add(Branch(name)))
    }

    fun modify(context: Context): Branch {
        val id   = context.params.getFirst("id")
        val name = context.params.getFirst("name") ?: ""

        return usecase.modify(Branch(id?.toLongOrNull(), name))
    }

    fun remove(context: Context) {
        val id = context.params.getFirst("id")
  
        return usecase.remove(id?.toLongOrNull())
    }

    fun addUser(context: Context, userUseCase: UserInteractor): UserPresenter {
        val branchId = context.params.getFirst("id")
        val userId   = context.params.getFirst("user_id")

        usecase.addUser(userId?.toLong(), branchId?.toLong(), userUseCase)

        return UserPresenter(userUseCase.userById(userId?.toLongOrNull()))
    }
}
