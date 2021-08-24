package com.lynas

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
import io.github.resilience4j.timelimiter.TimeLimiterConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.gateway.filter.factory.RequestRateLimiterGatewayFilterFactory
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import java.time.Duration


@EnableDiscoveryClient
@SpringBootApplication
class ApiGatewayApplication {

    @Bean
    fun defaultCustomizer(): Customizer<ReactiveResilience4JCircuitBreakerFactory> {
        return Customizer<ReactiveResilience4JCircuitBreakerFactory> { factory ->
            factory.configureDefault { id ->
                Resilience4JConfigBuilder(id)
                    .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
                    .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(1)).build())
                    .build()
            }
        }
    }

    @Bean
    fun userKeyResolver(): KeyResolver {
//        return KeyResolver { it.request.queryParams.getFirst("user").toMono() }
        return KeyResolver { Mono.just("1") }
    }

    @Bean
    fun redisRateLimiter() : RedisRateLimiter = RedisRateLimiter(2,2)


    @Bean
    fun gateways(rlb: RouteLocatorBuilder): RouteLocator {
        return rlb.routes()
            .route("bookRoute") { it
                    .path("/bookList")
                    .filters { f -> f
                            .setPath("/books")
                            .circuitBreaker { cb -> cb.setFallbackUri("forward:/emptyList") }
                            .requestRateLimiter().configure { rrc ->
                                rrc.setRateLimiter(redisRateLimiter())
                                rrc.setKeyResolver(userKeyResolver())

                            }
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
    fun emptyList(): List<Any> = listOf()

}