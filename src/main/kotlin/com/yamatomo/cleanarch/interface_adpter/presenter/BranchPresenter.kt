package com.yamatomo.cleanarch.interface_adpter.presenter

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonIgnore;

import com.yamatomo.cleanarch.domain.Branch

class BranchPresenter @JsonIgnore constructor(private val branch: Branch) {
    val id: Long
    val name: String

    init {
        id   = branch.id ?: -1
        name = branch.name
    }
}
