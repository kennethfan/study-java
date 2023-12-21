package io.github.kennethfan.netty.file.client.handler;

import io.github.kennethfan.netty.file.common.result.UploadSuccessResult;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChannelHandler.Sharable
public class UploadSuccessHandler extends SimpleChannelInboundHandler<UploadSuccessResult> {
    private static final UploadSuccessHandler INSTANCE = new UploadSuccessHandler();

    private UploadSuccessHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, UploadSuccessResult msg) throws Exception {
        log.info("{}/{} upload success", msg.getBasedir(), msg.getFilename());
    }

    public static UploadSuccessHandler getInstance() {
        return INSTANCE;
    }
}
