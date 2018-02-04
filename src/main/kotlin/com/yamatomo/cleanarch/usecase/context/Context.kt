package com.yamatomo.cleanarch.usecase.context

import org.springframework.util.MultiValueMap

data class Context constructor(
    val params: MultiValueMap<String, String?>,
    val container: DiContainer
)
