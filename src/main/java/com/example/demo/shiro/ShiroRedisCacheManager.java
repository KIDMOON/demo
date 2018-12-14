package com.example.demo.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.data.redis.connection.RedisConnection;

public class ShiroRedisCacheManager implements CacheManager {
    private RedisConnection redisConnection;
    private SessionRedisProperties properties;

    public ShiroRedisCacheManager(RedisConnection redisConnection, SessionRedisProperties properties) {
        this.redisConnection = redisConnection;
        this.properties = properties;
    }

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        return new ShiroRedisCache<K, V>(properties, name, redisConnection);
    }
}