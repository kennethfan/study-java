package io.github.kennethfan.netty.redis.protocol.response;

import io.netty.buffer.ByteBuf;

import static io.github.kennethfan.netty.redis.protocol.RedisFlag.NULL_STR_LENGTH;

public class MultiStringRedisDecoder implements RedisDecoder<String> {

    public static final MultiStringRedisDecoder INSTANCE = new MultiStringRedisDecoder();

    private MultiStringRedisDecoder() {
    }

    @Override
    public String decode(ByteBuf byteBuf) {
        byte[] strLengthBytes = readLine(byteBuf);
        if (strLengthBytes == null || strLengthBytes.length == 0) {
            return null;
        }

        int byteLength = Integer.parseInt(new String(strLengthBytes));
        if (byteLength == NULL_STR_LENGTH) {
            return null;
        }
        byte[] bytes = read(byteBuf, byteLength);
        readLineSeparator(byteBuf);
        return toString(bytes);
    }
}
