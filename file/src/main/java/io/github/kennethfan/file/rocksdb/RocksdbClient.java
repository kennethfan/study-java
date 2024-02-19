package io.github.kennethfan.file.rocksdb;

import io.github.kennethfan.file.rocksdb.codec.Decoder;
import io.github.kennethfan.file.rocksdb.codec.Encoder;
import io.github.kennethfan.file.rocksdb.codec.SerializableDecoder;
import io.github.kennethfan.file.rocksdb.codec.SerializableEncoder;
import io.github.kennethfan.file.rocksdb.iterator.RocksdbIterator;
import io.github.kennethfan.file.rocksdb.iterator.RocksdbRow;
import org.rocksdb.*;

import java.io.*;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RocksdbClient {

    private static final Map<String, RocksdbClient> instances = new ConcurrentHashMap<>();

    private RocksDB rocksDB;

    static {
        RocksDB.loadLibrary();
    }

    private RocksdbClient(String path) throws RocksDBException {
        Options options = new Options();
        options.setCreateIfMissing(true);
        this.rocksDB = RocksDB.open(options, path);
    }

    public static RocksdbClient newInstance(String path) {
        return instances.computeIfAbsent(path, path1 -> {
            try {
                return new RocksdbClient(path1);
            } catch (RocksDBException e) {
                return null;
            }
        });
    }

    public void put(byte[] key, byte[] value) throws RocksDBException {
        if (key == null || key.length == 0) {
            throw new IllegalArgumentException("key is null");
        }

        if (value == null || value.length == 0) {
            this.delete(key);
            return;
        }

        this.rocksDB.put(key, value);
    }

    public <K, V> void put(K key, Encoder<K> keyEncoder, V value, Encoder<V> valueEncoder) throws RocksDBException {
        this.put(keyEncoder.encode(key), valueEncoder.encode(value));
    }

    public <K> void put(K key, Encoder<K> keyEncoder, Serializable value) throws RocksDBException {
        this.put(keyEncoder.encode(key), SerializableEncoder.getInstance().encode(value));
    }

    public <V> void put(Serializable key, V value, Encoder<V> valueEncoder) throws RocksDBException {
        this.put(SerializableEncoder.getInstance().encode(key), valueEncoder.encode(value));
    }

    public void put(Serializable key, Serializable value) throws RocksDBException {
        Encoder<Serializable> encoder = SerializableEncoder.getInstance();
        this.put(encoder.encode(key), encoder.encode(value));
    }

    public byte[] get(byte[] key) throws RocksDBException {
        if (key == null || key.length == 0) {
            throw new IllegalArgumentException("key is null");
        }

        return this.rocksDB.get(key);
    }

    public <V extends Serializable> V get(byte[] key, Class<V> clazz) throws RocksDBException {
        byte[] bytes = this.get(key);
        return SerializableDecoder.newInstance(clazz).decode(bytes);
    }

    public <V> V get(byte[] key, Decoder<V> decoder) throws RocksDBException {
        byte[] bytes = this.get(key);
        return decoder.decode(bytes);
    }

    public byte[] get(Serializable key) throws RocksDBException {
        return this.get(SerializableEncoder.getInstance().encode(key));
    }

    public <V extends Serializable> V get(Serializable key, Class<V> clazz) throws RocksDBException {
        return this.get(SerializableEncoder.getInstance().encode(key), clazz);
    }

    public <V> V get(Serializable key, Decoder<V> decoder) throws RocksDBException {
        return this.get(SerializableEncoder.getInstance().encode(key), decoder);
    }

    public <K> byte[] get(K key, Encoder<K> encoder) throws RocksDBException {
        return this.get(encoder.encode(key));
    }

    public <K, V extends Serializable> V get(K key, Encoder<K> encoder, Class<V> clazz) throws RocksDBException {
        return this.get(encoder.encode(key), clazz);
    }

    public <K, V> V get(K key, Encoder<K> encoder, Decoder<V> decoder) throws RocksDBException {
        return this.get(encoder.encode(key), decoder);
    }

    public void delete(byte[] key) throws RocksDBException {
        if (key == null || key.length == 0) {
            throw new IllegalArgumentException("key is null");
        }

        this.rocksDB.delete(key);
    }

    public void delete(Serializable key) throws RocksDBException {
        this.delete(SerializableEncoder.getInstance().encode(key));
    }

    public <K> void delete(K key, Encoder<K> encoder) throws RocksDBException {
        this.delete(encoder.encode(key));
    }

    public <K, V> Iterator<RocksdbRow<K, V>> iterator(Decoder<K> keyDecoder, Decoder<V> valueDecoder) {
        return RocksdbIterator.newInstance(this.rocksDB.newIterator(new ReadOptions()), keyDecoder, valueDecoder);
    }

    public <K, V extends Serializable> Iterator<RocksdbRow<K, V>> iterator(Decoder<K> keyDecoder, Class<V> valueClass) {
        return RocksdbIterator.newInstance(this.rocksDB.newIterator(new ReadOptions()), keyDecoder, valueClass);
    }

    public <K extends Serializable, V> Iterator<RocksdbRow<K, V>> iterator(Class<K> keyClass, Decoder<V> valueDecoder) {
        return RocksdbIterator.newInstance(this.rocksDB.newIterator(new ReadOptions()), keyClass, valueDecoder);
    }

    public <K extends Serializable, V extends Serializable> Iterator<RocksdbRow<K, V>> iterator(Class<K> keyClass, Class<V> valueClass) {
        return RocksdbIterator.newInstance(this.rocksDB.newIterator(new ReadOptions()), keyClass, valueClass);
    }
}
