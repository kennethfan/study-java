package io.github.kennethfan.netty.file.client.handler;

import io.github.kennethfan.netty.file.common.ChannelContext;
import io.github.kennethfan.netty.file.common.result.AuthSuccessResult;
import io.github.kennethfan.netty.file.common.session.Session;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChannelHandler.Sharable
public class AuthSuccessHandler extends SimpleChannelInboundHandler<AuthSuccessResult> {

    private static final AuthSuccessHandler INSTANCE = new AuthSuccessHandler();

    private AuthSuccessHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, AuthSuccessResult msg) throws Exception {
        log.error("auth succeed");
        Session.put(ctx.channel(), ChannelContext.getUsername(ctx.channel()));
    }

    public static AuthSuccessHandler getInstance() {
        return INSTANCE;
    }
}
