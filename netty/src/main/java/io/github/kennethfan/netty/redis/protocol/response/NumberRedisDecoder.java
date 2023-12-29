package io.github.kennethfan.netty.redis.protocol.response;

import io.netty.buffer.ByteBuf;

public class NumberRedisDecoder implements RedisDecoder<Long> {

    public static final NumberRedisDecoder INSTANCE = new NumberRedisDecoder();

    private NumberRedisDecoder() {
    }

    @Override
    public Long decode(ByteBuf byteBuf) {
        byte[] bytes = readLine(byteBuf);

        String numStr = toString(bytes);
        return Long.parseLong(numStr);
    }
}
