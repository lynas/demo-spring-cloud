package com.example.eurekaservicediscovery

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer

@EnableEurekaServer
@SpringBootApplication
class EurekaServiceDiscoveryApplication

fun main(args: Array<String>) {
    runApplication<EurekaServiceDiscoveryApplication>(*args)
}
