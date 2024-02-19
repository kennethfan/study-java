package io.github.kennethfan.file.rocksdb.codec;

public interface Decoder<T> {
    T decode(byte[] bytes);
}
