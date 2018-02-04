package com.yamatomo.cleanarch.domain

data class User(val id: Long?, val firstName: String, val lastName: String, val branches: List<Branch>) {
    constructor(id: Long?, firstName: String, lastName: String): this(id, firstName, lastName, listOf())
    constructor(firstName: String, lastName: String): this(null, firstName, lastName, listOf())
    constructor(attrs: Map<String, Any?>): this(
        attrs["id"]        as? Long? ?: -1,
        attrs["firstName"] as? String ?: "",
        attrs["lastName"]  as? String ?: "",
        attrs["branches"]  as? List<Branch> ?: listOf()
    )
}
