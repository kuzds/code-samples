# Spring Boot Redis

## Local development
Start local Redis
```shell
docker-compose -f docker-compose-redis-only.yml -p redis-only up -d
```
Redis CLI:
```shell
docker exec -it redis-only-cache-1 redis-cli -a pass123
```
Get Redis keys
```
keys *
```