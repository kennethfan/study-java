package io.github.kennethfan.netty.redis.handler;

import io.github.kennethfan.netty.redis.protocol.response.ErrorRedisDecoder;
import io.github.kennethfan.netty.redis.protocol.response.RedisDecoder;
import io.github.kennethfan.netty.redis.protocol.response.SimpleStringRedisDecoder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static io.github.kennethfan.netty.redis.connection.RedisConnection.RESULT;

@Slf4j
public class RedisResponseDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        byte prefix = in.readByte();
        RedisDecoder handler = RedisDecoder.getDecoder(prefix);
        if (handler == null) {
            log.error("unknown data={}", (char) prefix);
            ctx.channel().close();
            return;
        }

        Object data = handler.decode(in);
        if (handler instanceof ErrorRedisDecoder) {
            ctx.channel().attr(RESULT).get().tryFailure(new RuntimeException((String) data));
            return;
        }

        ctx.channel().attr(RESULT).get().trySuccess(handler instanceof SimpleStringRedisDecoder ? null : data);
    }
}
