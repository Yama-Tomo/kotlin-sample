package com.yamatomo.cleanarch.domain

import com.yamatomo.cleanarch.domain.User

data class Branch(val id: Long?, val name: String, val users: List<User>) {
    constructor(id: Long?, name: String): this(id, name, listOf())
    constructor(name: String): this(null, name, listOf())
    constructor(attrs: Map<String, Any?>): this(
        attrs.get("id")    as? Long? ?: -1,
        attrs.get("name")  as? String ?: "",
        attrs.get("users") as? List<User> ?: listOf()
    )
}
