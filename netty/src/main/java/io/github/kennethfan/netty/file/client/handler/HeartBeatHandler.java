package io.github.kennethfan.netty.file.client.handler;

import io.github.kennethfan.netty.file.common.command.HeartBeatCommand;
import io.github.kennethfan.netty.file.common.protocol.Request;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
@ChannelHandler.Sharable
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {

    private static final HeartBeatHandler INSTANCE = new HeartBeatHandler();

    // 5秒发送一次心跳
    private static final int HEARTBEAT_INTERVAL = 5;

    private HeartBeatHandler() {
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        log.info("HeartBeatTimerHandler.channelActive");
        scheduleSendHeartBeat(ctx);

        super.channelActive(ctx);
    }

    private void scheduleSendHeartBeat(ChannelHandlerContext ctx) {
        // 此处无需使用scheduleAtFixedRate，因为如果通道失效后，就无需在发起心跳了，按照目前的方式是最好的：成功一次安排一次
        ctx.executor().schedule(() -> {

            if (ctx.channel().isActive()) {
//                log.info("定时任务发送心跳！");
                ctx.writeAndFlush(new Request().setCommand(new HeartBeatCommand()));
                scheduleSendHeartBeat(ctx);
            }

        }, HEARTBEAT_INTERVAL, TimeUnit.SECONDS);
    }

    public static HeartBeatHandler getInstance() {
        return INSTANCE;
    }
}
