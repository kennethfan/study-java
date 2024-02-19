package io.github.kennethfan.file.rocksdb.codec;

public interface Encoder<T> {
    byte[] encode(T object);
}
