package io.github.kennethfan.file.rocksdb.iterator;

public class RocksdbRow<K, V> {
    private K key;
    private V value;

    public RocksdbRow(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
}
