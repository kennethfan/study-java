package io.github.kennethfan.file.rocksdb.codec;

import java.nio.charset.StandardCharsets;

public class StringEncoder implements Encoder<String> {

    private static final StringEncoder INSTANCE = new StringEncoder();

    private StringEncoder() {
    }

    @Override
    public byte[] encode(String object) {
        if (object == null) {
            return null;
        }

        return object.getBytes(StandardCharsets.UTF_8);
    }

    public static StringEncoder getInstance() {
        return INSTANCE;
    }
}
