package io.github.kennethfan.netty.file.client.handler;

import io.github.kennethfan.netty.file.common.protocol.Request;
import io.github.kennethfan.netty.file.common.protocol.codec.RequestCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChannelHandler.Sharable
public class RequestEncoder extends MessageToByteEncoder<Request> {

    private static final RequestEncoder INSTANCE = new RequestEncoder();

    private RequestEncoder() {
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Request msg, ByteBuf out) throws Exception {
        RequestCodec.getInstance().encode(out, msg);
    }

    public static RequestEncoder getInstance() {
        return INSTANCE;
    }
}
