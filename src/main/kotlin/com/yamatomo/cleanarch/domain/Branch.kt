package com.yamatomo.cleanarch.domain

data class Branch(val id: Long?, val name: String, val users: List<User>) {
    constructor(id: Long?, name: String): this(id, name, listOf())
    constructor(name: String): this(null, name, listOf())
    constructor(attrs: Map<String, Any?>): this(
        attrs["id"]    as? Long? ?: -1,
        attrs["name"]  as? String ?: "",
        attrs["users"] as? List<User> ?: listOf()
    )
}
