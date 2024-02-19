package io.github.kennethfan.file.rocksdb.codec;

import java.nio.charset.StandardCharsets;

public class StringDecoder implements Decoder<String> {

    private static final StringDecoder INSTANCE = new StringDecoder();

    private StringDecoder() {
    }

    @Override
    public String decode(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }

        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static StringDecoder getInstance() {
        return INSTANCE;
    }
}
