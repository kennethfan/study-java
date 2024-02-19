package io.github.kennethfan.file.rocksdb;

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

    public void put(Serializable key, Serializable value) throws RocksDBException {
        if (key == null) {
            throw new IllegalArgumentException("key is null");
        }


        this.put(object2bytes(key), object2bytes(value));
    }

    public byte[] get(byte[] key) throws RocksDBException {
        if (key == null || key.length == 0) {
            throw new IllegalArgumentException("key is null");
        }

        return this.rocksDB.get(key);
    }

    public byte[] get(Serializable key) throws RocksDBException {
        if (key == null) {
            throw new IllegalArgumentException("key is null");
        }

        return this.get(object2bytes(key));
    }

    public <T> T get(Serializable key, Class<T> clazz) throws RocksDBException {
        byte[] bytes = this.get(key);

        return bytes2Object(bytes, clazz);
    }

    public void delete(byte[] key) throws RocksDBException {
        if (key == null || key.length == 0) {
            throw new IllegalArgumentException("key is null");
        }

        this.rocksDB.delete(key);
    }

    public void delete(Serializable key) throws RocksDBException {
        if (key == null) {
            throw new IllegalArgumentException("key is null");
        }

        this.rocksDB.delete(object2bytes(key));
    }

    public <K, V> Iterator<RocksdbRow<K, V>> iterator(Class<K> kClass, Class<V> vClass) {
        return new RocksdbIterator<>(this.rocksDB.newIterator(new ReadOptions()), kClass, vClass);
    }

    public static class RocksdbRow<K, V> {
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

    public static class RocksdbIterator<K, V> implements Iterator<RocksdbRow<K, V>> {
        private RocksIterator rocksIterator;

        private Class<K> keyClazz;

        private Class<V> valueClazz;

        public RocksdbIterator(RocksIterator rocksIterator, Class<K> keyClazz, Class<V> valueClazz) {
            this.rocksIterator = rocksIterator;
            this.rocksIterator.seekToFirst();
            this.keyClazz = keyClazz;
            this.valueClazz = valueClazz;
        }

        @Override
        public boolean hasNext() {
            return rocksIterator.isValid();
        }

        @Override
        public RocksdbRow<K, V> next() {
            RocksdbRow<K, V> rocksdbRow = new RocksdbRow<>(bytes2Object(rocksIterator.key(), keyClazz), bytes2Object(rocksIterator.value(), valueClazz));
            rocksIterator.next();
            return rocksdbRow;
        }
    }

    static <T> T bytes2Object(byte[] bytes, Class<T> clazz) {
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

    static byte[] object2bytes(Serializable obj) {
        if (obj == null) {
            return null;
        }

        if (obj instanceof byte[]) {
            return (byte[]) obj;
        }

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            try (ObjectOutputStream oos = new ObjectOutputStream(out)) {
                oos.writeObject(obj);
                return out.toByteArray();
            }
        } catch (Exception e) {
            throw new RuntimeException("object encode error");
        }
    }
}
