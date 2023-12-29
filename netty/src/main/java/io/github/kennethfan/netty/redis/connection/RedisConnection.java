package io.github.kennethfan.netty.redis.connection;

import io.github.kennethfan.netty.redis.config.RedisConfig;
import io.github.kennethfan.netty.redis.exception.RedisException;
import io.github.kennethfan.netty.redis.handler.RedisRequestEncoder;
import io.github.kennethfan.netty.redis.handler.RedisResponseDecoder;
import io.github.kennethfan.netty.redis.protocol.command.RedisCommand;
import io.github.kennethfan.netty.redis.protocol.command.base.AuthCommand;
import io.github.kennethfan.netty.redis.protocol.command.base.SelectCommand;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.Promise;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class RedisConnection {

    public static final AttributeKey<Promise> RESULT = AttributeKey.newInstance("promise");

    private static final EventLoop EVENT_LOOP = new DefaultEventLoop();

    private RedisConfig redisConfig;

    private EventLoopGroup eventLoopGroup;

    private Channel channel;

    public RedisConnection(RedisConfig redisConfig, EventLoopGroup eventLoopGroup) {
        this.redisConfig = redisConfig;
        this.eventLoopGroup = eventLoopGroup;

        this.init();
    }

    public void updateChannel(Channel channel) {
        this.channel = channel;
    }

    protected void init() {
        connect();
        auth();
        selectDatabase();
    }

    private void connect() {
        Promise<Void> connectPromise = newPromise();
        Channel channel = createBootstrap().connect(redisConfig.getIp(), redisConfig.getPort())
                .addListener(future -> {
                    if (!future.isSuccess()) {
                        connectPromise.tryFailure(future.cause());
                        return;
                    }
                    connectPromise.trySuccess(null);
                }).channel();
        connectPromise.syncUninterruptibly();
        connectPromise.getNow();

        this.updateChannel(channel);
    }

    private Bootstrap createBootstrap() {
        return new Bootstrap()
                .group(this.eventLoopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, Boolean.TRUE)
                .option(ChannelOption.TCP_NODELAY, Boolean.TRUE)
                .handler(new LoggingHandler(LogLevel.DEBUG))
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast(new StringEncoder())
                                .addLast(new RedisRequestEncoder())
                                .addLast(new RedisResponseDecoder());
                    }
                });
    }

    private void auth() {
        String password = redisConfig.getPassword();
        if (StringUtils.isBlank(password)) {
            return;
        }

        Promise<Void> promise = sendAsync(AuthCommand.newInstance(password));
        try {
            promise.await();
        } catch (InterruptedException e) {
            throw new RedisException("auth failed", e);
        }

        promise.getNow();
    }

    private void selectDatabase() {
        int database = redisConfig.getDatabase();
        if (database < 0 || database > 15) {
            throw new IllegalArgumentException("database must between 0 and 15");
        }

        if (database == 0) {
            return;
        }

        Promise<Void> promise = sendAsync(SelectCommand.newInstance(database));
        try {
            promise.await();
        } catch (InterruptedException e) {
            throw new RedisException("select failed", e);
        }

        promise.getNow();
    }

    public boolean valid() {
        return this.channel != null && this.channel.isActive();
    }

    public void destroy() {
        if (this.channel != null && this.channel.isActive()) {
            this.channel.close();
        }
    }

    public <V> Promise<V> sendAsync(RedisCommand redisCommand) {
        Promise<V> promise = newPromise();
        channel.attr(RESULT).set(promise);
        channel.writeAndFlush(redisCommand)
                .addListener(future -> {
                    if (!future.isSuccess()) {
                        log.info("future={} failed", future);
                        promise.tryFailure(future.cause());
                        return;
                    }
                    log.info("send succeed, cmd={}", redisCommand);
                });

        return promise;
    }

    public <V> Promise<V> sendAsync(String data) {
        Promise<V> promise = newPromise();
        channel.attr(RESULT).set(promise);
        channel.writeAndFlush(data)
                .addListener(future -> {
                    if (!future.isSuccess()) {
                        log.info("future={} failed", future);
                        promise.tryFailure(future.cause());
                        return;
                    }
                    log.info("send succeed, data={}", data);
                });

        return promise;
    }

    protected <V> Promise<V> newPromise() {
        return new DefaultPromise<>(EVENT_LOOP);
    }
}
