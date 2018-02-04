package com.yamatomo.cleanarch.usecase

import com.yamatomo.cleanarch.domain.Branch
import com.yamatomo.cleanarch.usecase.context.DiContainer
import com.yamatomo.cleanarch.usecase.exception.*

class BranchInteractor constructor(private val container: DiContainer) {
    fun branchById(id: Long?): Branch {
        if (id == null) {
            throw InvalidParamsException("invalid id parameter")
        }

        return container.getBranchRepository().findById(id) ?: throw DataNotFoundException("not found")
    }

    fun branches(): List<Branch> {
        return container.getBranchRepository().findAll()
    }

    fun add(branch: Branch): Branch {
        // TODO: バリデーションの実装方法調査
        if (branch.name == "") {
            throw InvalidParamsException("invalid parameters")
        }

        return container.getBranchRepository().save(branch)
    }

    fun modify(branch: Branch): Branch {
        // TODO: バリデーションの実装方法調査
        if (branch.id == null || branch.name == "") {
            throw InvalidParamsException("invalid parameters")
        }

        val branchModifiedBefore = branchById(branch.id)
        val attributes = mutableMapOf(
            "id"   to branchModifiedBefore.id,
            "name" to branchModifiedBefore.name
        )
        if (branch.name != "") attributes["name"] = branch.name

        return container.getBranchRepository().save(Branch(attributes))
    }

    fun remove(id: Long?) {
        if (id == null) {
            throw InvalidParamsException("invalid id parameter")
        }
        branchById(id)
 
        container.getBranchRepository().remove(id)
    }

    fun addUser(userId: Long?, branchId: Long?) {
        if (userId == null || branchId == null) {
            throw InvalidParamsException("invalid id parameter")
        }

        container.getUserRepository().findById(userId) ?: throw DataNotFoundException("not found")

        val branch = branchById(branchId)
        if (branch.users.stream().filter { it.id == userId }.findFirst().isPresent) {
            throw InvalidParamsException("Already branch subscribed")
        }

        container.getBranchRepository().addUser(userId, branchId)
    }
}
