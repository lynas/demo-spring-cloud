# 1 Start config server

- Create a git repository and host it on github or any similar git hosting site
- cd into Config-Server directory
- run `./gradlew bootRun`

# 2 Start discovery server (eureka)
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

# 5 Start Prometheus
- Why needed
  - Collect application metrics data 
  - e.g how many time an url is being hit within given interval (e.g 5 min)
  - Sample query
    - http_server_requests_seconds_count
    - rate(http_server_requests_seconds_count[5m])
    - http_server_requests_seconds_count{uri="/books"}
- How to run
  - Download prometheus
  - cd into it
  - create a file named `prometheus.yml`
  - Put you prometheus config into it. Sample provide in this repo root 
  - Execute following command to run it
  - `./prometheus --config.file=prometheus.yml`

# 6 API-Gateway
- cd into api-gateway
- run `./gradlew bootRun`




