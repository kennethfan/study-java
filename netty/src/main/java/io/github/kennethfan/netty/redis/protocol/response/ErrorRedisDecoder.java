package io.github.kennethfan.netty.redis.protocol.response;

import io.netty.buffer.ByteBuf;

public class ErrorRedisDecoder implements RedisDecoder<String> {

    public static final ErrorRedisDecoder INSTANCE = new ErrorRedisDecoder();

    private ErrorRedisDecoder() {
    }

    @Override
    public String decode(ByteBuf byteBuf) {
        return SimpleStringRedisDecoder.INSTANCE.decode(byteBuf);
    }
}
