package io.github.kennethfan.netty.file.client.handler;

import io.github.kennethfan.netty.file.common.result.NoAuthResult;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChannelHandler.Sharable
public class NoAuthHandler extends SimpleChannelInboundHandler<NoAuthResult> {

    private static final NoAuthHandler INSTANCE = new NoAuthHandler();

    private NoAuthHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, NoAuthResult msg) throws Exception {
        log.error("no authed");
    }

    public static NoAuthHandler getInstance() {
        return INSTANCE;
    }
}
