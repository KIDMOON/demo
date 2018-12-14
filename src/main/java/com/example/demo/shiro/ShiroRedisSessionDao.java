package com.example.demo.shiro;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.types.Expiration;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author kid
 */
@Slf4j
public class ShiroRedisSessionDao extends EnterpriseCacheSessionDAO {

    private StringSerializer keySerializer = new StringSerializer();
    private ObjectSerializer valueSerializer = new ObjectSerializer();
    private SessionRedisProperties properties;
    private RedisConnection redisConnection;

    public ShiroRedisSessionDao(RedisConnection redisConnection,SessionRedisProperties properties){
        this.properties = properties;
        this.redisConnection = redisConnection;
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = super.doCreate(session);
        session.setTimeout(properties.getSessionTimeout().toMillis());
        saveSession(session);
        return sessionId;
    }

    /**
     * save session
     * @param session
     * @throws UnknownSessionException
     */
    private void saveSession(Session session) throws UnknownSessionException {
        if (session == null || session.getId() == null) {
            log.error("session or session id is null");
            throw new UnknownSessionException("session or session id is null");
        }
        log.trace("set session {} to redis",session.getId().toString());
        byte[] key = getSessionRedisKey(session.getId().toString());
        byte[] value = valueSerializer.serialize(session);
        redisConnection.set(key, value, Expiration.from(properties.getSessionTimeout()), RedisStringCommands.SetOption.upsert());
    }

    private byte[] getSessionRedisKey(String sessionId) {
        return keySerializer.serialize(properties.getRedisSessionKeyPrefix()+sessionId.toString());
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Set<Session> sessions = new HashSet<Session>();
        Set<byte[]> keys = redisConnection.keys(getSessionRedisKey("*"));
        if (CollectionUtil.isNotEmpty(keys)) {
            for (byte[] key:keys) {
                Session s = (Session) valueSerializer.deserialize(redisConnection.get(key));
                sessions.add(s);
            }
        }
        return sessions;
    }

    // 获取session
    @Override
    protected Session doReadSession(Serializable sessionId) {
        Session session = super.doReadSession(sessionId);
        if (session == null) {
            redisConnection.get(getSessionRedisKey(sessionId.toString()));
            session = (Session) valueSerializer.deserialize(redisConnection.get(getSessionRedisKey(sessionId.toString())));
            log.trace("read session:{} from redis", sessionId);
        }
        return session;
    }

    @Override
    protected void doUpdate(Session session) {
        super.doUpdate(session);
        saveSession(session);
    }

    @Override
    protected void doDelete(Session session) {
        log.trace("delete session:{} to redis", session.getId());
        super.doDelete(session);
        redisConnection.del(getSessionRedisKey(session.getId().toString()));
    }

    /*private void setSessionToThreadLocal(Serializable sessionId, Session s) {
        Map<Serializable, Session> sessionMap = (Map<Serializable, Session>) sessionsInThread.get();
        if (sessionMap == null) {
            sessionMap = new HashMap<Serializable, Session>();
            sessionsInThread.set(sessionMap);
        }
        sessionMap.put(sessionId, s);
    }

    private Session getSessionFromThreadLocal(Serializable sessionId) {
        Session s = null;

        if (sessionsInThread.get() == null) {
            return null;
        }

        Map<Serializable, Session> sessionMap = sessionsInThread.get();
        Session sessionInMemory = sessionMap.get(sessionId);
        if (sessionInMemory == null) {
            return null;
        }
        long duration = new Date().getTime() - sessionInMemory.getLastAccessTime().getTime();
        if (duration < properties.getTimeout().toMillis()) {
            s = sessionInMemory;
            log.trace("read session {} from memory",sessionId);
        } else {
            sessionMap.remove(sessionId);
            log.warn("session in memory expired");
        }
        return s;
    }*/
}
