package io.github.kennethfan.netty.redis.pool;

import io.github.kennethfan.netty.redis.config.RedisConfig;
import io.github.kennethfan.netty.redis.connection.RedisConnection;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

@Slf4j
public class RedisConnectionFactory extends BasePooledObjectFactory<RedisConnection> {

    private RedisConfig redisConfig;

    private EventLoopGroup eventLoopGroup;

    public RedisConnectionFactory(RedisConfig redisConfig) {
        this.redisConfig = redisConfig;
        this.eventLoopGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() * 2, new DefaultThreadFactory("redis-event-loop"));
    }

    @Override
    public RedisConnection create() throws Exception {
        log.debug("RedisConnectionFactory.create");
        return new RedisConnection(redisConfig, eventLoopGroup);
    }

    @Override
    public PooledObject<RedisConnection> wrap(RedisConnection obj) {
        log.debug("RedisConnectionFactory.wrap");
        return new DefaultPooledObject<>(obj);
    }

    @Override
    public boolean validateObject(PooledObject<RedisConnection> p) {
        log.debug("RedisConnectionFactory.validateObject");
        return p.getObject().valid();
    }

    @Override
    public void destroyObject(PooledObject<RedisConnection> p) throws Exception {
        log.debug("RedisConnectionFactory.destroyObject");
        p.getObject().destroy();
    }
}
