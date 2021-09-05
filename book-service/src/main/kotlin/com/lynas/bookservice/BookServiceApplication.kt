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
    fun getAllBooks() : Map<String, Book> = getAllBooksMap()

    @GetMapping("/books/{id}")
    fun getOneBook(@PathVariable id: String) = getAllBooksMap()[id]
}


class Book(
    val id : String,
    val name: String
)

fun getAllBooksMap(): Map<String, Book> = mapOf(
    "325dac11" to Book("325dac11", "Harry Potter2"),
    "a1447057" to Book("a1447057", "Spring in Action2"),
    "4d48c52d" to Book("4d48c52d", "The great expectation2"),
    "5b3d68d7" to Book("5b3d68d7", "The Wind Rises2")

)