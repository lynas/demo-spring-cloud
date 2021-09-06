# 1 Start config server

- Create a git repository and host it on github or any similar git hosting site
- cd into Config-Server directory
- run `./gradlew bootRun`

# 2 Start Service discovery server (eureka)
- cd into Discovery-Server (eureka-service-discovery) directory
- run `./gradlew bootRun`

# 3 Start micro services (e.g. book-service)
- cd into book-service
- run `./gradlew bootRun`

# 4 Redis server
- Run redis server to handle rate limiting
- `sudo apt-get install redis`
- `sudo /etc/init.d/redis-server status`
- `sudo /etc/init.d/redis-server start`


# 5 API-Gateway
- cd into api-gateway
- run `./gradlew bootRun`

# 6 Distributed tracing
- Download zipkin server
  - https://zipkin.io/pages/quickstart
- In the service you want to trace add follwoing
  - spring:
      zipkin:
        base-url: http://192.168.86.130:9411

# 7 Start Prometheus
- Why needed
  - Collect application metrics data 
  - e.g how many time an url is being hit within given interval (e.g 5 min)
  - Sample query
    - http_server_requests_seconds_count
    - rate(http_server_requests_seconds_count[5m])
    - http_server_requests_seconds_count{uri="/books"}
    - http_server_requests_seconds_count{status="404"}
- How to run
  - Download prometheus
  - cd into it
  - create a file named `prometheus.yml`
  - Put you prometheus config into it. Sample provide in this repo root 
  - Execute following command to run it
  - `./prometheus --config.file=prometheus.yml`


# 8 Grafana
- This is used for graphically representing your microservices
- How to run
  - Download graphana ( https://grafana.com/grafana/download )
  - Here stand alone is shown you can run it in docker or other system
  - Go do grafana bin dir and run follwoing command
  - `./grafana-server web`
  - view it at `http://localhost:3000/`
  - default user name and password `admin`
  - JVM sample dashboard id 4701
  - You can import it from dashboard pane on the left



# 9 MISC

- Run service in docker
- Run jre 11 docker container
  - `sudo docker run -it openjdk:11-jre-slim-buster bash`
- Copy file to container
  - `sudo docker cp book-service-0.0.1-SNAPSHOT.jar 92ab2045d24e:/root/book-service-0.0.1-SNAPSHOT.jar`
- Run file 
  - `java -jar jarName.jar`
- Make sure to set ip address in application.yml so that eureka can discover service and call it via ip

```
eureka:
  instance:
    prefer-ip-address: true
    ip-address: 172.17.0.2
  client:
    serviceUrl:
      defaultZone: http://192.168.86.130:8761/eureka/
```