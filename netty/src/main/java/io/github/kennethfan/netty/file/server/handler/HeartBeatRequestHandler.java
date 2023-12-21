package io.github.kennethfan.netty.file.server.handler;

import io.github.kennethfan.netty.file.common.command.HeartBeatCommand;
import io.github.kennethfan.netty.file.common.enums.CodecEnum;
import io.github.kennethfan.netty.file.common.protocol.Response;
import io.github.kennethfan.netty.file.common.result.HeartBeatResult;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChannelHandler.Sharable
public class HeartBeatRequestHandler extends SimpleChannelInboundHandler<HeartBeatCommand> {
    private static final HeartBeatRequestHandler INSTANCE = new HeartBeatRequestHandler();

    private HeartBeatRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeartBeatCommand msg) throws Exception {
        ctx.writeAndFlush(new Response().setCodec(CodecEnum.JSON.getCodec()).setResult(new HeartBeatResult()));
    }

    public static HeartBeatRequestHandler getInstance() {
        return INSTANCE;
    }
}
