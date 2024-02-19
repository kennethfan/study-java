package io.github.kennethfan.file.rocksdb.iterator;

import io.github.kennethfan.file.rocksdb.codec.Decoder;
import io.github.kennethfan.file.rocksdb.codec.SerializableDecoder;
import org.rocksdb.RocksIterator;

import java.io.Serializable;
import java.util.Iterator;

public class RocksdbIterator<K, V> implements Iterator<RocksdbRow<K, V>> {
    private RocksIterator rocksIterator;

    private Decoder<K> keyDecoder;

    private Decoder<V> valueDecoder;

    private RocksdbIterator(RocksIterator rocksIterator, Decoder<K> keyDecoder, Decoder<V> valueDecoder) {
        this.rocksIterator = rocksIterator;
        this.rocksIterator.seekToFirst();
        this.keyDecoder = keyDecoder;
        this.valueDecoder = valueDecoder;
    }

    @Override
    public boolean hasNext() {
        return rocksIterator.isValid();
    }

    @Override
    public RocksdbRow<K, V> next() {
        RocksdbRow<K, V> rocksdbRow = new RocksdbRow<>(keyDecoder.decode(rocksIterator.key()), valueDecoder.decode(rocksIterator.value()));
        rocksIterator.next();
        return rocksdbRow;
    }

    public static <K, V> RocksdbIterator<K, V> newInstance(RocksIterator rocksIterator, Decoder<K> kDecoder, Decoder<V> vDecoder) {
        return new RocksdbIterator<>(rocksIterator, kDecoder, vDecoder);
    }

    public static <K extends Serializable, V> RocksdbIterator<K, V> newInstance(RocksIterator rocksIterator, Class<K> kClass, Decoder<V> vDecoder) {
        return new RocksdbIterator<>(rocksIterator, SerializableDecoder.newInstance(kClass), vDecoder);
    }

    public static <K, V extends Serializable> RocksdbIterator<K, V> newInstance(RocksIterator rocksIterator, Decoder<K> kDecoder, Class<V> vClass) {
        return new RocksdbIterator<>(rocksIterator, kDecoder, SerializableDecoder.newInstance(vClass));
    }

    public static <K extends Serializable, V extends Serializable> RocksdbIterator<K, V> newInstance(RocksIterator rocksIterator, Class<K> kClass, Class<V> vClass) {
        return new RocksdbIterator<>(rocksIterator, SerializableDecoder.newInstance(kClass), SerializableDecoder.newInstance(vClass));
    }
}

