package com.yamatomo.cleanarch.domain

data class User(val id: Long?, val firstName: String, val lastName: String) {
    constructor(firstName: String, lastName: String): this(null, firstName, lastName)
    constructor(attrs: Map<String, Any?>): this(
        attrs.get("id")        as? Long? ?: -1,
        attrs.get("firstName") as? String ?: "",
        attrs.get("lastName")  as? String ?: ""
    )
}
