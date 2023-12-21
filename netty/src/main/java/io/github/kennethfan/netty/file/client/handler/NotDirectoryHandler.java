package io.github.kennethfan.netty.file.client.handler;

import io.github.kennethfan.netty.file.common.result.NotDirectoryResult;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChannelHandler.Sharable
public class NotDirectoryHandler extends SimpleChannelInboundHandler<NotDirectoryResult> {
    private static final NotDirectoryHandler INSTANCE = new NotDirectoryHandler();

    private NotDirectoryHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, NotDirectoryResult msg) throws Exception {
        log.info("{} not a directory", msg.getBasedir());
    }

    public static NotDirectoryHandler getInstance() {
        return INSTANCE;
    }
}
