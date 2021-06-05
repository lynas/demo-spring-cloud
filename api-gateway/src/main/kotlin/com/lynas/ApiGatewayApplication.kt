package com.lynas

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.context.annotation.Bean
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

@EnableDiscoveryClient
@SpringBootApplication

class ApiGatewayApplication{

    @Bean
    @LoadBalanced
    fun builder() = WebClient.builder()

    @Bean
    fun webClient(builder: WebClient.Builder) =  builder.build()

    @Bean
    fun init(webClient: WebClient) = CommandLineRunner {
        GlobalScope.launch {
            val users = webClient.get()
                .uri("http://demo-service-1/users")
                .retrieve()
                .awaitBody<String>()

//            users.forEach {
//                println(users.toString())
//            }

            println(users)
        }

    }
}

fun main(args: Array<String>) {
    runApplication<ApiGatewayApplication>(*args)
}


class User(
    val id : String,
    val name: String
)