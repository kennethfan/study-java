package io.github.kennethfan.netty.redis.protocol.response;

import io.netty.buffer.ByteBuf;

public class SimpleStringRedisDecoder implements RedisDecoder<String> {

    public static final SimpleStringRedisDecoder INSTANCE = new SimpleStringRedisDecoder();

    private SimpleStringRedisDecoder() {
    }

    @Override
    public String decode(ByteBuf byteBuf) {
        byte[] bytes = readLine(byteBuf);

        return toString(bytes);
    }
}
