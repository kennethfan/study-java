package io.github.kennethfan.netty.redis.protocol.response;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.List;

public class ArrayRedisDecoder implements RedisDecoder<List<Object>> {

    public static final ArrayRedisDecoder INSTANCE = new ArrayRedisDecoder();

    private ArrayRedisDecoder() {
    }

    @Override
    public List<Object> decode(ByteBuf byteBuf) {
        byte[] lengthBytes = readLine(byteBuf);
        if (lengthBytes == null || lengthBytes.length == 0) {
            return new ArrayList<>();
        }

        int size = Integer.parseInt(new String(lengthBytes));
        List<Object> data = new ArrayList<>(size);
        for (int i = 0 ;i< size; i++) {
            data.add(RedisDecoder.getDecoder(byteBuf.readByte()).decode(byteBuf));
        }

        return data;
    }
}
