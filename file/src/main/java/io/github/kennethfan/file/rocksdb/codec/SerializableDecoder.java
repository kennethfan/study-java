package io.github.kennethfan.file.rocksdb.codec;

import java.io.*;

public class SerializableDecoder<T extends Serializable> implements Decoder<T> {

    private Class<T> clazz;

    public SerializableDecoder(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T decode(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }

        Object result;
        try (ByteArrayInputStream in = new ByteArrayInputStream(bytes)) {
            try (ObjectInputStream ois = new ObjectInputStream(in)) {
                result = ois.readObject();
            }
        } catch (Exception e) {
            throw new RuntimeException("result decode error", e);
        }


        if (result == null) {
            return null;
        }

        if (clazz.isInstance(result)) {
            return (T) result;
        }

        throw new RuntimeException("result decode error");
    }

    public static <T extends Serializable> SerializableDecoder<T> newInstance(Class<T> clazz) {
        return new SerializableDecoder<>(clazz);
    }
}
