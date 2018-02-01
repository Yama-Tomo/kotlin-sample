package com.yamatomo.cleanarch.router

import org.springframework.http.HttpStatus
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.*
import org.springframework.beans.factory.annotation.Autowired

import com.yamatomo.cleanarch.domain.Branch
import com.yamatomo.cleanarch.usecase.BranchInteractor
import com.yamatomo.cleanarch.usecase.UserInteractor
import com.yamatomo.cleanarch.usecase.context.Context
import com.yamatomo.cleanarch.interface_adpter.controller.BranchController
import com.yamatomo.cleanarch.interface_adpter.presenter.BranchPresenter
import com.yamatomo.cleanarch.interface_adpter.presenter.UserPresenter
import com.yamatomo.cleanarch.interface_adpter.repository.BranchRepository
import com.yamatomo.cleanarch.infrastructure.database.Branch as BranchDataGateway
import com.yamatomo.cleanarch.interface_adpter.repository.UserRepository
import com.yamatomo.cleanarch.infrastructure.database.User as UserDataGateway

@RestController
@RequestMapping(value = "branches")
class Branches {
    @Autowired
    private lateinit var gateway: BranchDataGateway
    @Autowired
    private lateinit var userGateway: UserDataGateway

    @RequestMapping(method = arrayOf(RequestMethod.GET))
    fun getBranches(@RequestParam requestParams: MultiValueMap<String, String?>): List<BranchPresenter> {
        return BranchController(BranchInteractor(BranchRepository(gateway))).lists()
    }

    @RequestMapping(value = "{id}", method = arrayOf(RequestMethod.GET))
    fun get(@PathVariable("id") id: String, @RequestParam requestParams: MultiValueMap<String, String?>): BranchPresenter {
        requestParams.set("id", id)
        return BranchController(BranchInteractor(BranchRepository(gateway))).show(Context(requestParams))
    }

    @RequestMapping(value = "{id}", method = arrayOf(RequestMethod.PUT))
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun put(@PathVariable("id") id: String, @RequestParam requestParams: MultiValueMap<String, String?>) {
        requestParams.set("id", id)
        BranchController(BranchInteractor(BranchRepository(gateway))).modify(Context(requestParams))
    }

    @RequestMapping(method = arrayOf(RequestMethod.POST))
    fun post(@RequestParam requestParams: MultiValueMap<String, String?>): BranchPresenter {
        return BranchController(BranchInteractor(BranchRepository(gateway))).add(Context(requestParams))
    }

    @RequestMapping(value = "{id}", method = arrayOf(RequestMethod.DELETE))
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun delete(@PathVariable("id") id: String, @RequestParam requestParams: MultiValueMap<String, String?>) {
        requestParams.set("id", id)
        BranchController(BranchInteractor(BranchRepository(gateway))).remove(Context(requestParams))
    }

    @RequestMapping(value = "{id}/users", method = arrayOf(RequestMethod.POST))
    fun postUser(@PathVariable("id") id: String, @RequestParam requestParams: MultiValueMap<String, String?>): UserPresenter {
        requestParams.set("id", id)
        val userUseCase = UserInteractor(UserRepository(userGateway))
        return BranchController(BranchInteractor(BranchRepository(gateway))).addUser(Context(requestParams), userUseCase)
    }
}