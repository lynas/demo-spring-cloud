package com.lynas

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean


@EnableDiscoveryClient
@SpringBootApplication
class ApiGatewayApplication {



    @Bean
    fun gateway(rlb: RouteLocatorBuilder): RouteLocator {
        return rlb.routes()
            .route {rs->rs
                .path("/userss")
                .uri("https://stackoverflow.com/")
            }
            .route { rs ->
                rs
                    .path("/users2")
//                    .filters { fs-> fs.circuitBreaker { cb->cb.setFallbackUri("forward:/userss") } }
                    .uri("lb://demo-service-1/users")
            }
            .build()

    }


//    @Bean
//    fun gateways(rlb: RouteLocatorBuilder): RouteLocator {
//        return rlb.routes()
//            .route {
//                it.path("/users").uri("lb://demo-service-1/users")
//            }.build()
//    }

}
fun main(args: Array<String>) {
    runApplication<ApiGatewayApplication>(*args)
}

