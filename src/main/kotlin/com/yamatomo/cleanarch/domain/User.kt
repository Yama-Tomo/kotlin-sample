package com.yamatomo.cleanarch.domain

import com.yamatomo.cleanarch.domain.Branch

data class User(val id: Long?, val firstName: String, val lastName: String, val branches: List<Branch>) {
    constructor(id: Long?, firstName: String, lastName: String): this(id, firstName, lastName, listOf())
    constructor(firstName: String, lastName: String): this(null, firstName, lastName, listOf())
    constructor(attrs: Map<String, Any?>): this(
        attrs.get("id")        as? Long? ?: -1,
        attrs.get("firstName") as? String ?: "",
        attrs.get("lastName")  as? String ?: "",
        attrs.get("branches")  as? List<Branch> ?: listOf()
    )
}
