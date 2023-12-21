package io.github.kennethfan.netty.file.client.handler;

import io.github.kennethfan.netty.file.common.protocol.codec.ResponseCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResponseSplitor extends LengthFieldBasedFrameDecoder {

    private ResponseSplitor() {
        super(Integer.MAX_VALUE, 16, 4);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf inByteBuf) throws Exception {
        log.info("ResponseSplitor.decode");
        if (inByteBuf.getInt(inByteBuf.readerIndex()) != ResponseCodec.MAGIC_NUMBER) {
            ctx.channel().close();
            return null;
        }

        return super.decode(ctx, inByteBuf);
    }

    public static ResponseSplitor newInstance() {
        return new ResponseSplitor();
    }
}
