package io.github.kennethfan.netty.file.client.handler;

import io.github.kennethfan.netty.file.common.result.DownloadSuccessResult;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;

@Slf4j
@ChannelHandler.Sharable
public class DownloadSuccessHandler extends SimpleChannelInboundHandler<DownloadSuccessResult> {
    private static final DownloadSuccessHandler INSTANCE = new DownloadSuccessHandler();

    private DownloadSuccessHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DownloadSuccessResult msg) throws Exception {
        log.info("{}/{} download success", msg.getBasedir(), msg.getFilename());
        String path = Paths.get(msg.getFilename()).toAbsolutePath().getFileName().toString();

        File file = new File(path);
        if (file.exists()) {
            log.warn("{} exists", path);
            return;
        }

        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(msg.getData());
        }
    }
    public static DownloadSuccessHandler getInstance() {
        return INSTANCE;
    }
}
