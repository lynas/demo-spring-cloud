package com.example.demoservice1

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class DemoService1Application

fun main(args: Array<String>) {
    runApplication<DemoService1Application>(*args)
}

@RefreshScope
@RestController
class MessageRestController {

    @Value("\${message:Hello defalult}")
    lateinit var propMessage: String

    @GetMapping("/message")
    fun getMessage() = propMessage
}