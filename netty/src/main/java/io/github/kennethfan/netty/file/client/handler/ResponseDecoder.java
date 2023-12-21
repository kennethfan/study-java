package io.github.kennethfan.netty.file.client.handler;

import io.github.kennethfan.netty.file.common.protocol.codec.ResponseCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ChannelHandler.Sharable
public class ResponseDecoder extends MessageToMessageDecoder<ByteBuf> {

    private static final ResponseDecoder INSTANCE = new ResponseDecoder();

    private ResponseDecoder() {
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        log.info("ResponseDecoder.decode");
        out.add(ResponseCodec.getInstance().decode(msg));
    }

    public static ResponseDecoder getInstance() {
        return INSTANCE;
    }
}
