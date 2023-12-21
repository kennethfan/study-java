package io.github.kennethfan.netty.file.client.handler;

import io.github.kennethfan.netty.file.common.result.FileExistsResult;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChannelHandler.Sharable
public class FileExistsHandler extends SimpleChannelInboundHandler<FileExistsResult> {
    private static final FileExistsHandler INSTANCE = new FileExistsHandler();

    private FileExistsHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FileExistsResult msg) throws Exception {
        log.info("{}/{} exists", msg.getBasedir(), msg.getFilename());
    }

    public static FileExistsHandler getInstance() {
        return INSTANCE;
    }
}
