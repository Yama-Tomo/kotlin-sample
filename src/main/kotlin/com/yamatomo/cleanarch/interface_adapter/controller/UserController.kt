package com.yamatomo.cleanarch.interface_adapter.controller

/**
 このレイヤの役割
  - パラメータを受け取る
  - ユースケースに扱いやすいデータの変換を行う
    - 主に型の変換
      型がユースケースの求めるものになっているかのチェクはするがバリデーションはしない(例：XXX文字以内である，メールアドレスに準拠した文字列である等々)
  - ユースケースを使って適切なビジネスロジックを実行
  -適切なレスポンスを返す
**/

import com.yamatomo.cleanarch.domain.User
import com.yamatomo.cleanarch.usecase.UserInteractor
import com.yamatomo.cleanarch.usecase.context.Context
import com.yamatomo.cleanarch.interface_adapter.presenter.UserPresenter

class UserController constructor(private val context: Context) {
    fun show(): UserPresenter {
        val id      = context.params.getFirst("id")
        val useCase = UserInteractor(context.container)
  
		return UserPresenter(useCase.userById(id?.toLongOrNull()))
    }

    fun lists(): List<UserPresenter> {
        val useCase = UserInteractor(context.container)
        return useCase.users().map { UserPresenter(it) }
    }

    fun add(): UserPresenter {
        val firstName = context.params.getFirst("first_name") ?: ""
        val lastName  = context.params.getFirst("last_name")  ?: ""
        val useCase   = UserInteractor(context.container)

        return UserPresenter(useCase.add(User(firstName, lastName)))
    }

    fun modify(): User {
        val id        = context.params.getFirst("id")
        val firstName = context.params.getFirst("first_name") ?: ""
        val lastName  = context.params.getFirst("last_name")  ?: ""
        val useCase   = UserInteractor(context.container)

        return useCase.modify(User(id?.toLongOrNull(), firstName, lastName, listOf()))
    }

    fun remove() {
        val id      = context.params.getFirst("id")
        val useCase = UserInteractor(context.container)

        return useCase.remove(id?.toLongOrNull())
    }
}
