package com.lynas

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@EnableDiscoveryClient
@SpringBootApplication
class ApiGatewayApplication {

    @Bean
    fun gateways(rlb: RouteLocatorBuilder): RouteLocator {
        return rlb.routes()
            .route("userRoute") {
                it
                    .path("/bookList")
                    .filters { f-> f
                        .setPath("/books")
                        .circuitBreaker { cb -> cb.setFallbackUri("forward:/emptyList") }
                    }
                    .uri("lb://book-service")
            }.build()
    }

}
fun main(args: Array<String>) {
    runApplication<ApiGatewayApplication>(*args)
}

@RestController
class FallBackController {

    @GetMapping("/emptyList")
    fun emptyList() : List<Any> = listOf()

}