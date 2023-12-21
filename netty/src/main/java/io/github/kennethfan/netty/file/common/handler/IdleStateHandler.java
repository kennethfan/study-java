package io.github.kennethfan.netty.file.common.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChannelHandler.Sharable
public class IdleStateHandler extends io.netty.handler.timeout.IdleStateHandler {

    private static IdleStateHandler INSTANCE = new IdleStateHandler();

    private IdleStateHandler() {
        super(60, 60, 60);
    }

    /**
     * 测试
     *
     * @return
     */
    public static IdleStateHandler getInstance() {
        return INSTANCE;
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        log.info("channel {} closed", ctx.channel().id().asShortText());
        ctx.channel().close();
    }
}
