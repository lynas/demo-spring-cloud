## Test api gateway

http :9999/bookList


## Test rate limiting

for i in {1..10}; do http :9999/bookList; done

