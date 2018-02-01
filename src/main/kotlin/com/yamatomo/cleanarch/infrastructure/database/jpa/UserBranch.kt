package com.yamatomo.cleanarch.infrastructure.database.jpa

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
@Table(name="user_branches")
data class UserBranch(
    @Id @GeneratedValue
    val id: Long?,
    @Column(name = "user_id")
    val userId: Long,
    @Column(name = "branch_id")
    val branchId: Long
) {
}

