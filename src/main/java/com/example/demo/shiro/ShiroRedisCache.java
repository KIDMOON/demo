package com.example.demo.shiro;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.session.mgt.SimpleSession;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.types.Expiration;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author kid
 */
@Slf4j
public class ShiroRedisCache <K, V> implements Cache<K, V> {

    private SessionRedisProperties properties;
    private RedisConnection redisConnection;
    private StringSerializer keySerializer = new StringSerializer();
    private ObjectSerializer valueSerializer = new ObjectSerializer();
    private final static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<>();

    public ShiroRedisCache(SessionRedisProperties properties,String name, RedisConnection redisConnection) {
        this.redisConnection = redisConnection;
        this.properties = properties;
    }

    private byte[] getShiroRedisKey(Object k) {
        return keySerializer.serialize(properties.getRedisShiroKeyPrefix()+SecureUtil.md5(JSON.toJSONString(k)));
    }

    @SuppressWarnings("unchecked")
    @Override
    public V get(K key) throws CacheException {

        V s = getSessionFromThreadLocal(key);
        if (s != null) {
            log.trace("read session : {} from threadLocal", key);
            return s;
        }

        log.trace("read session : {} from redis", key);
        byte[] ks = getShiroRedisKey(key);
        V v= (V) valueSerializer.deserialize(redisConnection.get(ks));
        setSessionToThreadLocal(key, v);
        return v;
    }

    @Override
    public V put(K key, V value) throws CacheException {
        log.trace("add session : {} value : {}", key,value);
        V old = get(key);
        redisConnection.set(getShiroRedisKey(key), valueSerializer.serialize(value), Expiration.from(properties.getSessionTimeout()), RedisStringCommands.SetOption.upsert());
        return old;
    }

    @Override
    public V remove(K key) throws CacheException {
        log.trace("delete session : {}", key);
        V old = get(key);
        redisConnection.del(getShiroRedisKey(key));
        return old;
    }

    @Override
    public void clear() throws CacheException {
        Set<K> keys = keys();
        if(CollectionUtil.isNotEmpty(keys)) {
            for(K k:keys) {
                redisConnection.del((byte[]) k);
            }
        }
    }

    @Override
    public int size() {
        return keys().size();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<K> keys() {
        return (Set<K>) redisConnection.keys(getShiroRedisKey("*"));
    }

    @Override
    public Collection<V> values() {
        Set<K> set = keys();
        List<V> list = new ArrayList<>();
        for (K s : set) {
            list.add(get(s));
        }
        return list;
    }



    private void setSessionToThreadLocal(K k, V s) {
        if(s!=null && s.getClass().isAssignableFrom(SimpleSession.class)) {
            Map<String, Object> sessionMap =threadLocal.get();
            if (sessionMap == null) {
                sessionMap = new HashMap<>();
                threadLocal.set(sessionMap);
            }
            sessionMap.put((String) k, s);
        }
    }

    private V getSessionFromThreadLocal(K k) {
        Object s = null;

        if (threadLocal.get() == null) {
            return null;
        }

        Map<String, Object> sessionMap = threadLocal.get();
        Object sessionInMemory = sessionMap.get(k);
        if (sessionInMemory == null) {
            return null;
        }
        if(sessionInMemory.getClass().isAssignableFrom(SimpleSession.class)) {
            SimpleSession session = (SimpleSession)sessionInMemory;
            long duration = System.currentTimeMillis() - session.getLastAccessTime().getTime();
            if (duration < properties.getSessionTimeout().toMillis()) {
                s = sessionInMemory;
                log.trace("read session {} from threadLocal",k);
            } else {
                sessionMap.remove(k);
                log.warn("session in threadLocal expired");
            }
        }
        return (V) s;
    }
}
