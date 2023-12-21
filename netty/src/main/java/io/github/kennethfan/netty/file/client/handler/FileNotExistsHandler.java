package io.github.kennethfan.netty.file.client.handler;

import io.github.kennethfan.netty.file.common.result.FileNotExistsResult;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChannelHandler.Sharable
public class FileNotExistsHandler extends SimpleChannelInboundHandler<FileNotExistsResult> {
    private static final FileNotExistsHandler INSTANCE = new FileNotExistsHandler();

    private FileNotExistsHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FileNotExistsResult msg) throws Exception {
        log.info("{}/{} not exists", msg.getBasedir(), msg.getFilename());
    }

    public static FileNotExistsHandler getInstance() {
        return INSTANCE;
    }
}
