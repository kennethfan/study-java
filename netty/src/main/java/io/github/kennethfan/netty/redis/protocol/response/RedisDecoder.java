package io.github.kennethfan.netty.redis.protocol.response;

import io.netty.buffer.ByteBuf;

import java.nio.charset.StandardCharsets;

import static io.github.kennethfan.netty.redis.protocol.RedisFlag.*;

public interface RedisDecoder<T> {

    static RedisDecoder getDecoder(byte prefix) {
        switch (prefix) {
            case ERROR_FLAG:
                return ErrorRedisDecoder.INSTANCE;
            case SIMPLE_STRING_FLAG:
                return SimpleStringRedisDecoder.INSTANCE;
            case MULTI_STRING_FLAG:
                return MultiStringRedisDecoder.INSTANCE;
            case NUMBER_FLAG:
                return NumberRedisDecoder.INSTANCE;
            case ARRAY_FLAG:
                return ArrayRedisDecoder.INSTANCE;
            default:
                return null;
        }
    }

    T decode(ByteBuf byteBuf);

    default void readLineSeparator(ByteBuf byteBuf) {
        byteBuf.readByte();
        byteBuf.readByte();
    }

    default byte[] readLine(ByteBuf byteBuf) {
        int len = byteBuf.bytesBefore(CR_FLAG);
        byte[] bytes = new byte[len];
        byteBuf.readBytes(bytes);

        readLineSeparator(byteBuf);

        return bytes;
    }

    default byte[] read(ByteBuf byteBuf, int n) {
        if (n == 0) {
            return new byte[0];
        }

        byte[] bytes = new byte[n];
        byteBuf.readBytes(bytes);

        return bytes;
    }

    default String toString(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }

        return new String(bytes, StandardCharsets.UTF_8);
    }
}
