package io.github.kennethfan.netty.file.server.handler;

import io.github.kennethfan.netty.file.common.command.ListCommand;
import io.github.kennethfan.netty.file.common.enums.CodecEnum;
import io.github.kennethfan.netty.file.common.protocol.Response;
import io.github.kennethfan.netty.file.common.result.ListResult;
import io.github.kennethfan.netty.file.common.result.NotDirectoryResult;
import io.github.kennethfan.netty.file.common.result.model.FileItem;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@ChannelHandler.Sharable
public class ListRequestHandler extends SimpleChannelInboundHandler<ListCommand> {

    private static final ListRequestHandler INSTANCE = new ListRequestHandler();

    private ListRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListCommand msg) throws Exception {
        log.info("ListRequestHandler.channelRead0");

        String rootPath = Paths.get(System.getProperty("user.home"), msg.getBasedir()).toAbsolutePath().toString();
        log.info("rootPath={}", rootPath);
        File directory = new File(rootPath);
        if (!directory.isDirectory()) {
            ctx.writeAndFlush(new Response().setCodec(CodecEnum.JSON.getCodec()).setResult(NotDirectoryResult.newInstance(msg.getBasedir())));
            return;
        }

        List<FileItem> fileItemList = new ArrayList<>();
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            FileItem fileItem = new FileItem()
                    .setName(file.getName())
                    .setType(file.isFile() ? "file" : "directory")
                    .setSize(file.length());
            fileItemList.add(fileItem);
        }
        ListResult result = new ListResult()
                .setBasedir(msg.getBasedir())
                .setFileItemList(fileItemList);
        Response response = new Response()
                .setCodec(CodecEnum.JSON.getCodec())
                .setResult(result);
        ctx.writeAndFlush(response);
    }

    public static ListRequestHandler getInstance() {
        return INSTANCE;
    }
}
