package com.yamatomo.cleanarch.router

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.*

import com.yamatomo.cleanarch.usecase.context.Context
import com.yamatomo.cleanarch.interface_adapter.controller.UserController
import com.yamatomo.cleanarch.interface_adapter.presenter.UserPresenter
import com.yamatomo.cleanarch.infrastructure.DiContainerImpl
import com.yamatomo.cleanarch.infrastructure.database.User as UserDataGateway

@RestController
@RequestMapping(value = ["users"])
class Users @Autowired constructor(
    private val container: DiContainerImpl
) {
    @Autowired
    private lateinit var gateway: UserDataGateway

    @RequestMapping(method = [(RequestMethod.GET)])
    fun getUsers(@RequestParam requestParams: MultiValueMap<String, String?>): List<UserPresenter> {
        return UserController(Context(requestParams, container)).lists()
    }

    @RequestMapping(value = ["{id}"], method = [(RequestMethod.GET)])
    fun get(@PathVariable("id") id: String, @RequestParam requestParams: MultiValueMap<String, String?>): UserPresenter {
        requestParams.set("id", id)
        return UserController(Context(requestParams, container)).show()
    }

    @RequestMapping(value = ["{id}"], method = [(RequestMethod.PUT)])
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun put(@PathVariable("id") id: String, @RequestParam requestParams: MultiValueMap<String, String?>) {
        requestParams.set("id", id)
        UserController(Context(requestParams, container)).modify()
    }

    @RequestMapping(method = [(RequestMethod.POST)])
    fun post(@RequestParam requestParams: MultiValueMap<String, String?>): UserPresenter {
        return UserController(Context(requestParams, container)).add()
    }

    @RequestMapping(value = ["{id}"], method = [(RequestMethod.DELETE)])
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun delete(@PathVariable("id") id: String, @RequestParam requestParams: MultiValueMap<String, String?>) {
        requestParams.set("id", id)
        UserController(Context(requestParams, container)).remove()
    }
}
