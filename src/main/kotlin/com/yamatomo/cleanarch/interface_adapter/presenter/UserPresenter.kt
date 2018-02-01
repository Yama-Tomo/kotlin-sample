package com.yamatomo.cleanarch.interface_adapter.presenter

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonIgnore;

import com.yamatomo.cleanarch.domain.User
import com.yamatomo.cleanarch.domain.Branch
import com.yamatomo.cleanarch.interface_adapter.presenter.BranchPresenter

class UserPresenter @JsonIgnore constructor(private val user: User) {
    val id: Long

    @JsonProperty("first_name")
    val firstName: String

    @JsonProperty("last_name")
    val lastName: String

    val branches: List<BranchPresenter>

    init {
        id        = user.id ?: -1
        firstName = user.firstName
        lastName  = user.lastName
        branches  = user.branches.map { BranchPresenter(it) }
    }
}
