package io.github.kennethfan.netty.file.client.handler;

import io.github.kennethfan.netty.file.common.result.ListResult;
import io.github.kennethfan.netty.file.common.result.model.FileItem;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Optional;

@Slf4j
@ChannelHandler.Sharable
public class ListSuccessHandler extends SimpleChannelInboundHandler<ListResult> {
    private static final ListSuccessHandler INSTANCE = new ListSuccessHandler();

    private ListSuccessHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListResult msg) throws Exception {
        log.info("list success, basedir={}", msg.getBasedir());
        Optional.ofNullable(msg.getFileItemList())
                .orElse(new ArrayList<>())
                .forEach(this::logFileItem);

    }

    private void logFileItem(FileItem fileItem) {
        log.info("{}\t{}\t{}", fileItem.getName(), fileItem.getType(), fileItem.getSize());
    }

    public static ListSuccessHandler getInstance() {
        return INSTANCE;
    }
}
