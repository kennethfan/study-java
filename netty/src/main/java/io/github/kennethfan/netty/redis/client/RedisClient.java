package io.github.kennethfan.netty.redis.client;

import io.github.kennethfan.netty.redis.config.RedisConfig;
import io.github.kennethfan.netty.redis.connection.RedisConnection;
import io.github.kennethfan.netty.redis.exception.RedisException;
import io.github.kennethfan.netty.redis.pool.RedisConnectionPool;
import io.github.kennethfan.netty.redis.protocol.command.RedisCommand;
import io.netty.util.concurrent.Promise;

public class RedisClient {


    private RedisConnectionPool pool;

    public RedisClient(RedisConfig config) {
        this.pool = new RedisConnectionPool(config);
    }

    public <V> Promise<V> sendAsync(String data) {
        RedisConnection connection = this.pool.getConnection();
        Promise<V> promise = connection.sendAsync(data);
        promise.addListener(future -> RedisClient.this.pool.releaseConnection(connection));
        return promise;
    }

    public <V> Promise<V> sendAsync(RedisCommand cmd) {
        RedisConnection connection = this.pool.getConnection();
        Promise<V> promise = connection.sendAsync(cmd);
        promise.addListener(future -> RedisClient.this.pool.releaseConnection(connection));
        return promise;
    }

    public <V> V send(String data) {
        Promise<V> promise = sendAsync(data);

        return get(promise);
    }

    public <V> V send(RedisCommand cmd) {
        Promise<V> promise = sendAsync(cmd);

        return get(promise);
    }

    private <V> V get(Promise<V> promise) {
        try {
            promise.await();
        } catch (InterruptedException e) {
            throw new RedisException("redis executed failed", e);
        }

        if (!promise.isSuccess()) {
            throw new RedisException(promise.cause());
        }

        return promise.getNow();
    }
}
