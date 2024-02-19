package io.github.kennethfan.file.rocksdb;

import io.github.kennethfan.file.rocksdb.iterator.RocksdbRow;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Slf4j
public class RocksdbMain {

    private static final String path = System.getProperty("user.home") + "/data/crawldb";

    public static void main(String[] args) throws Exception {
        RocksdbClient rocksdbClient = RocksdbClient.newInstance(path);

        rocksdbClient.put("hello", "world");
        log.info("key=hello, value={}", rocksdbClient.get("hello", String.class));

        rocksdbClient.put("hello", null);
        log.info("key=hello, value={}", rocksdbClient.get("hello", String.class));

        List<String> keys = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            String key = UUID.randomUUID().toString().replace("-", "");
            String value = UUID.randomUUID().toString().replace("-", "");
            rocksdbClient.put(key, value);
            if (i % 2 == 0) {
                keys.add(key);
            }
        }

        Iterator<RocksdbRow<String, String>> iterator = rocksdbClient.iterator(String.class, String.class);
        int i = 0;
        while (iterator.hasNext()) {
            RocksdbRow<String, String> row = iterator.next();
            log.info("i={}, key={}, value={}", i++, row.getKey(), row.getValue());
        }

        for (String key : keys) {
            rocksdbClient.delete(key);
        }

        iterator = rocksdbClient.iterator(String.class, String.class);
        i = 0;
        while (iterator.hasNext()) {
            RocksdbRow<String, String> row = iterator.next();
            log.info("i={}, key={}, value={}", i++, row.getKey(), row.getValue());
        }
    }
}
