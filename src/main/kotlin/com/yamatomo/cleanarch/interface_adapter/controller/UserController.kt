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
import com.yamatomo.cleanarch.usecase.exception.*
import com.yamatomo.cleanarch.usecase.UserInteractor
import com.yamatomo.cleanarch.usecase.context.Context
import com.yamatomo.cleanarch.interface_adapter.presenter.UserPresenter

class UserController constructor(private val usecase: UserInteractor) {
    fun show(context: Context): UserPresenter {
        val id = context.params.getFirst("id")
  
		return UserPresenter(usecase.userById(id?.toLongOrNull()))
    }

    fun lists(): List<UserPresenter> {
        return usecase.users().map { UserPresenter(it) }
    }

    fun add(context: Context): UserPresenter {
        val firstName = context.params.getFirst("first_name") ?: ""
        val lastName  = context.params.getFirst("last_name")  ?: ""

        return UserPresenter(usecase.add(User(firstName, lastName)))
    }

    fun modify(context: Context): User {
        val id        = context.params.getFirst("id")
        val firstName = context.params.getFirst("first_name") ?: ""
        val lastName  = context.params.getFirst("last_name")  ?: ""

        return usecase.modify(User(id?.toLongOrNull(), firstName, lastName, listOf()))
    }

    fun remove(context: Context) {
        val id = context.params.getFirst("id")
  
        return usecase.remove(id?.toLongOrNull())
    }
}
