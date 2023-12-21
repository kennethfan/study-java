package io.github.kennethfan.netty.file.server;

import io.github.kennethfan.netty.file.common.PropertyUtil;
import io.github.kennethfan.netty.file.common.handler.IdleStateHandler;
import io.github.kennethfan.netty.file.server.handler.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyServer {

    public static void main(String[] args) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        serverBootstrap
                .group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel ch) {
                        ch.pipeline().addLast(IdleStateHandler.getInstance());
                        ch.pipeline().addLast(RequestSplitor.newInstance());
                        ch.pipeline().addLast(RequestDecoder.getInstance());
                        ch.pipeline().addLast(ResponseEncoder.getInstance());
                        ch.pipeline().addLast(AuthHandler.getInstance());
                        ch.pipeline().addLast(LoginRequestHandler.getInstance());
                        ch.pipeline().addLast(ListRequestHandler.getInstance());
                        ch.pipeline().addLast(UploadRequestHandler.getInstance());
                        ch.pipeline().addLast(DownloadRequestHandler.getInstance());
                        ch.pipeline().addLast(HeartBeatRequestHandler.getInstance());
                    }
                });
        int port = PropertyUtil.getPort();
        serverBootstrap.bind(port)
                .addListener(future -> {
                    if (future.isSuccess()) {
                        log.info("端口{}绑定成功！", port);
                    } else {
                        log.error("端口{}绑定失败！", port);
                    }
                });
    }
}
