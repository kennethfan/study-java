package io.github.kennethfan.netty.file.server.handler;

import io.github.kennethfan.netty.file.common.command.Command;
import io.github.kennethfan.netty.file.common.command.LoginCommand;
import io.github.kennethfan.netty.file.common.enums.CodecEnum;
import io.github.kennethfan.netty.file.common.protocol.Response;
import io.github.kennethfan.netty.file.common.result.NoAuthResult;
import io.github.kennethfan.netty.file.common.session.Session;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@ChannelHandler.Sharable
public class AuthHandler extends SimpleChannelInboundHandler<Command> {

    private static final AuthHandler INSTANCE = new AuthHandler();

    private AuthHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Command msg) throws Exception {
        log.info("AuthHandler.channelRead0");
        // 登录请求，直接放行
        if (msg instanceof LoginCommand) {
            ctx.fireChannelRead(msg);
            return;
        }

        if (Session.get(ctx.channel()) == null) {
            log.error("未授权, channel={}", ctx.channel().id().asShortText());
            Response response = new Response()
                    .setCodec(CodecEnum.JSON.getCodec())
                    .setResult(NoAuthResult.getInstance());
            ctx.writeAndFlush(response);
            return;
        }

        ctx.fireChannelRead(msg);
    }

    public static AuthHandler getInstance() {
        return INSTANCE;
    }
}
