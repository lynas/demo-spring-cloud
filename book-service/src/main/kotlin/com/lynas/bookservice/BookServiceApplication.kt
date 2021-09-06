package com.lynas.bookservice

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import java.util.*


@EnableDiscoveryClient
@SpringBootApplication
class BookServiceApplication {
    @Bean
    fun webClientOrderService(builder: WebClient.Builder) = builder.baseUrl("lb://order-service").build()

    @Bean
    fun routeLocator(routeLocatorBuilder: RouteLocatorBuilder): RouteLocator {
        return routeLocatorBuilder.routes()
            .route("order-service") { route ->
                route.path("/order/**")
                    .uri("lb://order-service")
            }
            .build()
    }

    @Bean
    @LoadBalanced
    fun loadBalancedWebClientBuilder(): WebClient.Builder? {
        return WebClient.builder()
    }
}

fun main(args: Array<String>) {
    runApplication<BookServiceApplication>(*args)
}

@RefreshScope
@RestController
class BookRestController(
    val webClient: WebClient
) {

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

    @GetMapping("/books/order/{id}")
    fun buyBook(@PathVariable id: String) = webClient.get().uri("/order/$id").retrieve().bodyToMono<String>()
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