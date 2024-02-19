package io.github.kennethfan.file.rocksdb.codec;

import java.io.*;

public class SerializableEncoder implements Encoder<Serializable> {
    private static final SerializableEncoder INSTANCE = new SerializableEncoder();

    private SerializableEncoder() {
    }

    @Override
    public byte[] encode(Serializable object) {
        if (object == null) {
            return null;
        }

        if (object instanceof byte[]) {
            return (byte[]) object;
        }

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            try (ObjectOutputStream oos = new ObjectOutputStream(out)) {
                oos.writeObject(object);
                return out.toByteArray();
            }
        } catch (Exception e) {
            throw new RuntimeException("object encode error");
        }
    }

    public static SerializableEncoder getInstance() {
        return INSTANCE;
    }
}
