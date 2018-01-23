package com.yamatomo.cleanarch

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class CleanarchApplication

fun main(args: Array<String>) {
    SpringApplication.run(CleanarchApplication::class.java, *args)
}
