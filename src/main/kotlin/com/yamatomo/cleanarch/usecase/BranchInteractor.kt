package com.yamatomo.cleanarch.usecase

import com.yamatomo.cleanarch.domain.User
import com.yamatomo.cleanarch.domain.Branch
import com.yamatomo.cleanarch.usecase.exception.*
import com.yamatomo.cleanarch.usecase.BranchRepository
import com.yamatomo.cleanarch.usecase.UserInteractor

class BranchInteractor constructor(private val repos: BranchRepository) {
    fun branchById(id: Long?): Branch {
        if (id == null) {
            throw InvalidParamsException("invalid id parameter");
        }
 
        val branch = repos.findById(id)
        if (branch == null) {
            throw DataNotFoundException("not found");
        }

        return branch
    }

    fun branches(): List<Branch> {
        return repos.findAll()
    }

    fun add(branch: Branch): Branch {
        // TODO: バリデーションの実装方法調査
        if (branch.name == "") {
            throw InvalidParamsException("invalid parameters");
        }

        return repos.save(branch)
    }

    fun modify(branch: Branch): Branch {
        // TODO: バリデーションの実装方法調査
        if (branch.id == null || branch.name == "") {
            throw InvalidParamsException("invalid parameters");
        }

        val branchModifiedBefore = branchById(branch.id)
        val attributes = mutableMapOf<String, Any?>(
            "id"   to branchModifiedBefore.id,
            "name" to branchModifiedBefore.name
        )
        if (branch.name != "") attributes.set("name", branch.name)

        return repos.save(Branch(attributes))
    }

    fun remove(id: Long?) {
        if (id == null) {
            throw InvalidParamsException("invalid id parameter");
        }
        branchById(id)
 
        repos.remove(id)
    }

    fun addUser(userId: Long?, branchId: Long?, userUseCase: UserInteractor) {
        if (userId == null || branchId == null) {
            throw InvalidParamsException("invalid id parameter");
        }

        val user   = userUseCase.userById(userId)
        val branch = branchById(branchId)

        if (branch.users.stream().filter { it.id == userId }.findFirst().isPresent()) {
            throw InvalidParamsException("Already branch subscribed");
        }

        repos.addUser(userId, branchId)
    }
}
