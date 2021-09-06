package com.lynas.orderservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@EnableDiscoveryClient
@SpringBootApplication
class OrderServiceApplication

fun main(args: Array<String>) {
    runApplication<OrderServiceApplication>(*args)
}

@RestController
class OrderRestController {


    @GetMapping("/order/{bookId}")
    fun orderABook(@PathVariable bookId:String) : String {
        return "Order process complete for book : ${bookId}"
    }
}