package com.yamatomo.cleanarch.infrastructure.database.jpa

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToMany

import com.yamatomo.cleanarch.infrastructure.database.jpa.Branch

@Entity
@Table(name="users")
data class User(
    @Id @GeneratedValue
    val id: Long?,
    @Column(name = "first_name")
    val firstName: String,
    @Column(name = "last_name")
    val lastName: String
) {
	@ManyToMany(mappedBy = "users")
	val branches: List<Branch> = listOf()
}
