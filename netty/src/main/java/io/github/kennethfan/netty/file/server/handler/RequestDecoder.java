package io.github.kennethfan.netty.file.server.handler;

import io.github.kennethfan.netty.file.common.protocol.codec.RequestCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ChannelHandler.Sharable
public class RequestDecoder extends MessageToMessageDecoder<ByteBuf> {

    public static final RequestDecoder INSTANCE = new RequestDecoder();

    private RequestDecoder() {
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        log.info("RequestDecoder.decode");
        out.add(RequestCodec.getInstance().decode(msg));
    }

    public static RequestDecoder getInstance() {
        return INSTANCE;
    }
}
