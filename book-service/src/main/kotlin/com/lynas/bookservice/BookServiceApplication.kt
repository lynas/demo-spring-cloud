package com.lynas.bookservice

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@EnableDiscoveryClient
@SpringBootApplication
class BookServiceApplication

fun main(args: Array<String>) {
    runApplication<BookServiceApplication>(*args)
}

@RefreshScope
@RestController
class MessageRestController {

    @Value("\${message:Hello default}")
    lateinit var propMessage: String

    @GetMapping("/message")
    fun getMessage() = propMessage

    @GetMapping("/greeting/{name}")
    fun greeting(@PathVariable name: String) = "Hello $name"

    @GetMapping("/books")
    fun getUsers() = listOf(
        Book(UUID.randomUUID().toString(), "Harry Potter"),
        Book(UUID.randomUUID().toString(), "Spring in Action"),
        Book(UUID.randomUUID().toString(), "The great expectation"),
        Book(UUID.randomUUID().toString(), "The Wind Rises")
    )
}


class Book(
    val id : String,
    val name: String
)