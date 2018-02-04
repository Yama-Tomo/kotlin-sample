package com.yamatomo.cleanarch.interface_adapter.presenter

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonIgnore

import com.yamatomo.cleanarch.domain.User

class UserPresenter @JsonIgnore constructor(private val user: User) {
    val id: Long = user.id ?: -1

    @JsonProperty("first_name")
    val firstName: String = user.firstName

    @JsonProperty("last_name")
    val lastName: String = user.lastName

    val branches: List<BranchPresenter> = user.branches.map { BranchPresenter(it) }
}
