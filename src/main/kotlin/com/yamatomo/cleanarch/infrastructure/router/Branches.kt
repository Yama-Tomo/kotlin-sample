package com.yamatomo.cleanarch.router

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.*

import com.yamatomo.cleanarch.usecase.context.Context
import com.yamatomo.cleanarch.interface_adapter.controller.BranchController
import com.yamatomo.cleanarch.interface_adapter.presenter.BranchPresenter
import com.yamatomo.cleanarch.interface_adapter.presenter.UserPresenter
import com.yamatomo.cleanarch.infrastructure.DiContainerImpl

@RestController
@RequestMapping(value = ["branches"])
class Branches @Autowired constructor(
    private val container: DiContainerImpl
) {
    @RequestMapping(method = [(RequestMethod.GET)])
    fun getBranches(@RequestParam requestParams: MultiValueMap<String, String?>): List<BranchPresenter> {
        return BranchController(Context(requestParams, container)).lists()
    }

    @RequestMapping(value = ["{id}"], method = [(RequestMethod.GET)])
    fun get(@PathVariable("id") id: String, @RequestParam requestParams: MultiValueMap<String, String?>): BranchPresenter {
        requestParams.set("id", id)
        return BranchController(Context(requestParams, container)).show()
    }

    @RequestMapping(value = ["{id}"], method = [(RequestMethod.PUT)])
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun put(@PathVariable("id") id: String, @RequestParam requestParams: MultiValueMap<String, String?>) {
        requestParams.set("id", id)
        BranchController(Context(requestParams, container)).modify()
    }

    @RequestMapping(method = [(RequestMethod.POST)])
    fun post(@RequestParam requestParams: MultiValueMap<String, String?>): BranchPresenter {
        return BranchController(Context(requestParams, container)).add()
    }

    @RequestMapping(value = ["{id}"], method = [(RequestMethod.DELETE)])
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun delete(@PathVariable("id") id: String, @RequestParam requestParams: MultiValueMap<String, String?>) {
        requestParams.set("id", id)
        BranchController(Context(requestParams, container)).remove()
    }

    @RequestMapping(value = ["{id}/users"], method = [(RequestMethod.POST)])
    fun postUser(@PathVariable("id") id: String, @RequestParam requestParams: MultiValueMap<String, String?>): UserPresenter {
        requestParams.set("id", id)
        return BranchController(Context(requestParams, container)).addUser()
    }
}
