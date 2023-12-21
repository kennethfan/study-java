package io.github.kennethfan.netty.file.server.handler;

import io.github.kennethfan.netty.file.common.command.LoginCommand;
import io.github.kennethfan.netty.file.common.enums.CodecEnum;
import io.github.kennethfan.netty.file.common.protocol.Response;
import io.github.kennethfan.netty.file.common.result.AuthFailedResult;
import io.github.kennethfan.netty.file.common.result.AuthSuccessResult;
import io.github.kennethfan.netty.file.common.session.Session;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
@ChannelHandler.Sharable
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginCommand> {

    private static final LoginRequestHandler INSTANCE = new LoginRequestHandler();

    private LoginRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginCommand msg) throws Exception {
        if (!StringUtils.equalsIgnoreCase("123456", msg.getPasswd())) {
            log.error("密码错误, channel={}", ctx.channel().id().asShortText());
            Response response = new Response()
                    .setCodec(CodecEnum.JSON.getCodec())
                    .setResult(AuthFailedResult.getInstance());

            ctx.writeAndFlush(response);
            return;
        }

        log.info("登录成功, channel={}", ctx.channel().id().asShortText());
        Session.put(ctx.channel(), msg.getUsername());
        Response response = new Response()
                .setCodec(CodecEnum.JSON.getCodec())
                .setResult(AuthSuccessResult.getInstance());

        ctx.writeAndFlush(response);
    }


    public static LoginRequestHandler getInstance() {
        return INSTANCE;
    }
}

