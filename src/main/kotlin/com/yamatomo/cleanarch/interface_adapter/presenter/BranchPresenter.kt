package com.yamatomo.cleanarch.interface_adapter.presenter

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonIgnore;

import com.yamatomo.cleanarch.domain.Branch

class BranchPresenter @JsonIgnore constructor(private val branch: Branch) {
    val id: Long = branch.id ?: -1
    val name: String = branch.name
}
