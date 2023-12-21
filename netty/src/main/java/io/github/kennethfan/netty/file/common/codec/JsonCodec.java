package io.github.kennethfan.netty.file.common.codec;

import com.alibaba.fastjson.JSON;

import java.nio.charset.StandardCharsets;

public class JsonCodec implements ICodec {

    private static final JsonCodec INSTANCE = new JsonCodec();

    private JsonCodec() {}

    @Override
    public byte[] encode(Object data) {
        if (data == null) {
            return new byte[0];
        }

        return JSON.toJSONString(data).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public <T> T decode(byte[] bytes, Class<T> clazz) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }

        return JSON.parseObject(new String(bytes, StandardCharsets.UTF_8), clazz);
    }

    public static JsonCodec getInstance( ) {
        return INSTANCE;
    }
}
