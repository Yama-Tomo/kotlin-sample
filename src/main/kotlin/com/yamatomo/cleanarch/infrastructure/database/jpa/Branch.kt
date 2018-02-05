package com.yamatomo.cleanarch.infrastructure.database.jpa

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToMany
import javax.persistence.JoinTable
import javax.persistence.JoinColumn

@Entity
@Table(name="branches")
data class Branch(
    @Id @GeneratedValue
    val id: Long?,
    @Column()
    val name: String
    // TODO: 日付をPrePersist, PreUpdateを利用して記録する
) {
    @ManyToMany
    @JoinTable(
        name="user_branches",
        joinColumns = [(JoinColumn(name = "branch_id"))],
        inverseJoinColumns= [(JoinColumn(name = "user_id"))]
    )
    val users: List<User> = listOf()
}

