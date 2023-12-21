package io.github.kennethfan.netty.file.server.handler;

import io.github.kennethfan.netty.file.common.protocol.Response;
import io.github.kennethfan.netty.file.common.protocol.codec.ResponseCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChannelHandler.Sharable
public class ResponseEncoder extends MessageToByteEncoder<Response> {

    private static final ResponseEncoder INSTANCE = new ResponseEncoder();

    private ResponseEncoder() {
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Response msg, ByteBuf out) throws Exception {
        log.info("ResponseEncoder.encode");
        ResponseCodec.getInstance().encode(out, msg);
    }

    public static ResponseEncoder getInstance() {
        return INSTANCE;
    }
}
