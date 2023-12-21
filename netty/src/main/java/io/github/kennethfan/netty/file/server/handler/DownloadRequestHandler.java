package io.github.kennethfan.netty.file.server.handler;

import io.github.kennethfan.netty.file.common.command.DownloadCommand;
import io.github.kennethfan.netty.file.common.enums.CodecEnum;
import io.github.kennethfan.netty.file.common.protocol.Response;
import io.github.kennethfan.netty.file.common.result.DownloadSuccessResult;
import io.github.kennethfan.netty.file.common.result.FileNotExistsResult;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;

@Slf4j
@ChannelHandler.Sharable
public class DownloadRequestHandler extends SimpleChannelInboundHandler<DownloadCommand> {
    private static final DownloadRequestHandler INSTANCE = new DownloadRequestHandler();

    private DownloadRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DownloadCommand msg) throws Exception {
        log.info("DownloadRequestHandler.channelRead0");
        String filename = Paths.get(msg.getFilename()).toAbsolutePath().getFileName().toString();
        String fullPath = Paths.get(System.getProperty("user.home"), msg.getBasedir(), filename).toAbsolutePath().toString();

        File file = new File(fullPath);
        if (!file.exists()) {
            log.error("{} not exists", fullPath);
            FileNotExistsResult result = new FileNotExistsResult()
                    .setBasedir(msg.getBasedir())
                    .setFilename(msg.getFilename());
            ctx.writeAndFlush(new Response().setCodec(CodecEnum.JSON.getCodec()).setResult(result));
            return;
        }

        try {

            DownloadSuccessResult result = new DownloadSuccessResult()
                    .setBasedir(msg.getBasedir())
                    .setFilename(msg.getFilename());
            try (FileInputStream inputStream = new FileInputStream(file)) {
                byte[] bytes = new byte[inputStream.available()];
                inputStream.read(bytes);
                result.setData(bytes);
            }
            ctx.writeAndFlush(new Response().setCodec(CodecEnum.JSON.getCodec()).setResult(result));
        } catch (IOException e) {
            log.error("download error, fullPath={}", fullPath, e);
        }
    }

    public static DownloadRequestHandler getInstance() {
        return INSTANCE;
    }
}
