package io.github.kennethfan.netty.file.server.handler;

import io.github.kennethfan.netty.file.common.protocol.codec.RequestCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestSplitor extends LengthFieldBasedFrameDecoder {


    private RequestSplitor() {
        super(Integer.MAX_VALUE, 16, 4);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf inByteBuf) throws Exception {
        log.info("RequestSplitor.decode");
        if (inByteBuf.getInt(inByteBuf.readerIndex()) != RequestCodec.MAGIC_NUMBER) {
            ctx.channel().close();
            return null;
        }

        return super.decode(ctx, inByteBuf);
    }

    public static RequestSplitor newInstance() {
        return new RequestSplitor();
    }
}
