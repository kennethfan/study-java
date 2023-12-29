package io.github.kennethfan.netty.redis.pool;

import io.github.kennethfan.netty.redis.config.RedisConfig;
import io.github.kennethfan.netty.redis.connection.RedisConnection;
import io.github.kennethfan.netty.redis.exception.RedisException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class RedisConnectionPool {

    private final GenericObjectPool<RedisConnection> pool;

    public RedisConnectionPool(RedisConfig redisConfig) {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        if (redisConfig.getPoolConfig() != null) {
            RedisPoolConfig poolConfig = redisConfig.getPoolConfig();
            config.setMinIdle(poolConfig.getMinIdle());
            config.setMaxIdle(poolConfig.getMaxIdle());
            config.setMaxWaitMillis(poolConfig.getMaxWaitMills());
            config.setTestOnBorrow(config.getTestOnBorrow());
            config.setTestOnReturn(config.getTestOnReturn());
        }

        RedisConnectionFactory factory = new RedisConnectionFactory(redisConfig);
        this.pool = new GenericObjectPool(factory, config);

        initConnections();
    }

    public RedisConnection getConnection() {
        log.debug("get connection");
        try {
            return this.pool.borrowObject();
        } catch (Exception e) {
            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            }
            throw new RedisException("get redis connect error", e);
        }
    }

    public void releaseConnection(RedisConnection obj) {
        log.debug("release connection");
        this.pool.returnObject(obj);
    }

    protected void initConnections() {
        log.debug("init connections");
        try {
            int initSize = Math.max(1, this.pool.getMinIdle() / 2);
            List<RedisConnection> initConnections = new ArrayList<>(initSize);
            for (int i = 0; i < initSize; i++) {
                initConnections.add(this.getConnection());
            }
            initConnections.forEach(this::releaseConnection);
        } catch (Exception e) {
            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            }
            throw new RedisException("initConnections error", e);
        }
    }
}
