package io.github.kennethfan.netty.file.client.handler;

import io.github.kennethfan.netty.file.common.result.AuthFailedResult;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChannelHandler.Sharable
public class AuthFailedHandler extends SimpleChannelInboundHandler<AuthFailedResult> {

    private static final AuthFailedHandler INSTANCE = new AuthFailedHandler();

    private AuthFailedHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, AuthFailedResult msg) throws Exception {
        log.error("auth failed");
    }

    public static AuthFailedHandler getInstance() {
        return INSTANCE;
    }
}
