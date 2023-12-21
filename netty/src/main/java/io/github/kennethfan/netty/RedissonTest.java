package io.github.kennethfan.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RedissonTest {


    public Bootstrap start() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, Boolean.TRUE)
                .option(ChannelOption.TCP_NODELAY, Boolean.TRUE)
                .handler(new LoggingHandler(LogLevel.INFO))
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast(new StringDecoder())
                                .addLast(new StringEncoder())
                                .addLast(new RedisClientHandler());
                    }
                });

        return bootstrap;
    }

    public static void main(String[] args) throws Exception {
        Bootstrap bootstrap = new RedissonTest().start();
        ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6379).sync();
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {
                    log.info("连接成功");
                    Channel channel = channelFuture.sync().channel().writeAndFlush("AUTH 123456\r\n").sync().channel();
                    channel = channelFuture.sync().channel().writeAndFlush("SET A B\r\n").sync().channel();
                    log.info("auth id={}", channel.id());
                } else {
                    log.info("连接失败");
                }
            }
        });

//        ChannelFuture future = channel.writeAndFlush("AUTH 123456\r\n").sync();
//        log.info("authed");
//        future = future.await();
//        if (future.isDone()) {
//            future = channel.writeAndFlush("set a b").sync();
//        }

        while (true) {
            Thread.sleep(100000);
            log.info("sleep");

        }
    }
}
