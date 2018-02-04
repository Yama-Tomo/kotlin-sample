package com.yamatomo.cleanarch.usecase

/**
 この層の役割
  - interface_adapterからいい感じのデータが渡って来るので要件を満たす処理を実行
    - 要件を満たす過程で永続化さされたデータへのアクセスが必要な場合はDIPしたインターフェース越しにinterface_adapterへ移譲
**/

import com.yamatomo.cleanarch.domain.User
import com.yamatomo.cleanarch.usecase.context.DiContainer
import com.yamatomo.cleanarch.usecase.exception.*

class UserInteractor constructor(private val container: DiContainer) {
    fun userById(id: Long?): User {
        if (id == null) {
            throw InvalidParamsException("invalid id parameter")
        }

        return container.getUserRepository().findById(id) ?: throw DataNotFoundException("not found")
    }

    fun users(): List<User> {
        return container.getUserRepository().findAll()
    }

    fun add(user: User): User {
        // TODO: バリデーションの実装方法調査
        if (user.firstName == "" || user.lastName == "") {
            throw InvalidParamsException("invalid parameters")
        }

        return container.getUserRepository().save(user)
    }

    fun modify(user: User): User {
        // TODO: バリデーションの実装方法調査
        if (user.id == null || (user.firstName == "" && user.lastName == "")) {
            throw InvalidParamsException("invalid parameters")
        }

        val userModifiedBefore = userById(user.id)
        val attributes = mutableMapOf<String, Any?>(
            "id"        to userModifiedBefore.id,
            "firstName" to userModifiedBefore.firstName,
            "lastName"  to userModifiedBefore.lastName
        )
        if (user.firstName != "") attributes["firstName"] = user.firstName
        if (user.lastName != "")  attributes["lastName"] = user.lastName

        return container.getUserRepository().save(User(attributes))
    }

    fun remove(id: Long?) {
        if (id == null) {
            throw InvalidParamsException("invalid id parameter")
        }
        userById(id)
 
        container.getUserRepository().remove(id)
    }
}
