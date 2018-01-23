package com.yamatomo.cleanarch.interface_adpter.controller

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

class UserController constructor(private val usecase: UserInteractor) {
    fun show(context: Context): User {
        val id = context.params.getFirst("id")
  
        return usecase.userById(id?.toLongOrNull())
    }

    fun lists(): List<User> {
        return usecase.users()
    }

    fun add(context: Context): User {
        val firstName = context.params.getFirst("first_name") as? String ?: ""
        val lastName  = context.params.getFirst("last_name") as? String ?: ""

        return usecase.add(User(firstName, lastName))
    }

    fun modify(context: Context): User {
        val id        = context.params.getFirst("id")
        val firstName = context.params.getFirst("first_name") as? String ?: ""
        val lastName  = context.params.getFirst("last_name") as? String ?: ""

        return usecase.modify(User(id?.toLongOrNull(), firstName, lastName))
    }

    fun remove(context: Context) {
        val id = context.params.getFirst("id")
  
        return usecase.remove(id?.toLongOrNull())
    }
}
