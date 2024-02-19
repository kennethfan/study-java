package io.github.kennethfan.file.rocksdb.iterator;

import io.github.kennethfan.file.rocksdb.codec.Decoder;
import org.rocksdb.RocksIterator;

import java.util.Iterator;

public class RocksdbIterator<K, V> implements Iterator<RocksdbRow<K, V>> {
    private RocksIterator rocksIterator;

    private Decoder<K> keyDecoder;

    private Decoder<V> valueDecoder;

    public RocksdbIterator(RocksIterator rocksIterator, Decoder<K> keyDecoder, Decoder<V> valueDecoder) {
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
}

