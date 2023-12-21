package io.github.kennethfan.netty.file.server.handler;

import io.github.kennethfan.netty.file.common.command.UploadCommand;
import io.github.kennethfan.netty.file.common.enums.CodecEnum;
import io.github.kennethfan.netty.file.common.protocol.Response;
import io.github.kennethfan.netty.file.common.result.FileExistsResult;
import io.github.kennethfan.netty.file.common.result.UploadSuccessResult;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;

@Slf4j
@ChannelHandler.Sharable
public class UploadRequestHandler extends SimpleChannelInboundHandler<UploadCommand> {
    private static final UploadRequestHandler INSTANCE = new UploadRequestHandler();

    private UploadRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, UploadCommand msg) throws Exception {
        log.info("UploadRequestHandler.channelRead0");
        String filename = Paths.get(msg.getFilename()).toAbsolutePath().getFileName().toString();
        String fullPath = Paths.get(System.getProperty("user.home"), msg.getBasedir(), filename).toAbsolutePath().toString();

        File file = new File(fullPath);
        if (file.exists()) {
            log.error("{} exists", fullPath);
            FileExistsResult result = new FileExistsResult()
                    .setBasedir(msg.getBasedir())
                    .setFilename(msg.getFilename());
            ctx.writeAndFlush(new Response().setCodec(CodecEnum.JSON.getCodec()).setResult(result));
            return;
        }

        try {
            try (FileOutputStream outputStream = new FileOutputStream(file)) {
                outputStream.write(msg.getData());
            }
            UploadSuccessResult result = new UploadSuccessResult()
                    .setBasedir(msg.getBasedir())
                    .setFilename(msg.getFilename());
            ctx.writeAndFlush(new Response().setCodec(CodecEnum.JSON.getCodec()).setResult(result));
        } catch (IOException e) {
            log.error("upload error, fullPath={}", fullPath, e);
        }
    }

    public static UploadRequestHandler getInstance() {
        return INSTANCE;
    }
}
