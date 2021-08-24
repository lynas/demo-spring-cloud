## Test api gateway

http :9999/bookList


## Test rate limiting

`for i in {1..10}; do http :9999/bookList; done`

Result should include following 

```
HTTP/1.1 429 Too Many Requests
X-RateLimit-Burst-Capacity: 2
X-RateLimit-Remaining: 0
X-RateLimit-Replenish-Rate: 2
X-RateLimit-Requested-Tokens: 1
content-length: 0

```



