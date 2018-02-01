package com.yamatomo.cleanarch.router

import org.springframework.http.HttpStatus
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.*
import org.springframework.beans.factory.annotation.Autowired

import com.yamatomo.cleanarch.domain.User
import com.yamatomo.cleanarch.usecase.UserInteractor
import com.yamatomo.cleanarch.usecase.context.Context
import com.yamatomo.cleanarch.interface_adapter.controller.UserController
import com.yamatomo.cleanarch.interface_adapter.presenter.UserPresenter
import com.yamatomo.cleanarch.interface_adapter.repository.UserRepository
import com.yamatomo.cleanarch.infrastructure.database.User as UserDataGateway

@RestController
@RequestMapping(value = "users")
class Users {
    @Autowired
    private lateinit var gateway: UserDataGateway

    @RequestMapping(method = arrayOf(RequestMethod.GET))
    fun getUsers(@RequestParam requestParams: MultiValueMap<String, String?>): List<UserPresenter> {
        return UserController(UserInteractor(UserRepository(gateway))).lists()
    }

    @RequestMapping(value = "{id}", method = arrayOf(RequestMethod.GET))
    fun get(@PathVariable("id") id: String, @RequestParam requestParams: MultiValueMap<String, String?>): UserPresenter {
        requestParams.set("id", id)
        return UserController(UserInteractor(UserRepository(gateway))).show(Context(requestParams))
    }

    @RequestMapping(value = "{id}", method = arrayOf(RequestMethod.PUT))
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun put(@PathVariable("id") id: String, @RequestParam requestParams: MultiValueMap<String, String?>) {
        requestParams.set("id", id)
        UserController(UserInteractor(UserRepository(gateway))).modify(Context(requestParams))
    }

    @RequestMapping(method = arrayOf(RequestMethod.POST))
    fun post(@RequestParam requestParams: MultiValueMap<String, String?>): UserPresenter {
        return UserController(UserInteractor(UserRepository(gateway))).add(Context(requestParams))
    }

    @RequestMapping(value = "{id}", method = arrayOf(RequestMethod.DELETE))
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun delete(@PathVariable("id") id: String, @RequestParam requestParams: MultiValueMap<String, String?>) {
        requestParams.set("id", id)
        UserController(UserInteractor(UserRepository(gateway))).remove(Context(requestParams))
    }
}
