package io.github.kennethfan.netty.redis;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.*;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

@Slf4j
public class RedisMigration {


    private static final String SOURCE_IP = "redis.mig.source.ip";
    private static final String SOURCE_PORT = "redis.mig.source.port";
    private static final String SOURCE_PASSWORD = "redis.mig.source.password";
    private static final String SOURCE_DATABASE = "redis.mig.source.database";

    private static final String DESTINATION_IP = "redis.mig.destination.ip";
    private static final String DESTINATION_PORT = "redis.mig.destination.port";
    private static final String DESTINATION_PASSWORD = "redis.mig.destination.password";
    private static final String DESTINATION_DATABASE = "redis.mig.destination.database";

    public static void main(String args[]) {
        log.info("encoding={}", System.getProperty("file.encoding"));
        log.info("sip={}", System.getProperty("redis.mig.source.ip"));
        RedissonClient sourceClient = sourceClient();
        RedissonClient destClient = destClient();

        sourceClient.getKeys().getKeysStream().forEach(key -> migration(sourceClient, destClient, key));

    }

    private static RedissonClient sourceClient() {
        String ip = System.getProperty(SOURCE_IP);
        int port = Integer.parseInt(System.getProperty(SOURCE_PORT));
        String pass = System.getProperty(SOURCE_PASSWORD);
        int db = Integer.parseInt(System.getProperty(SOURCE_DATABASE));

        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://" + ip + ":" + port + "")
                .setPassword(pass)
                .setDatabase(db);
        return Redisson.create(config);
    }

    private static RedissonClient destClient() {
        String ip = System.getProperty(DESTINATION_IP);
        int port = Integer.parseInt(System.getProperty(DESTINATION_PORT));
        String pass = System.getProperty(DESTINATION_PASSWORD);
        int db = Integer.parseInt(System.getProperty(DESTINATION_DATABASE));


        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://" + ip + ":" + port + "")
                .setPassword(pass)
                .setDatabase(db);
        return Redisson.create(config);
    }

    private static void migration(RedissonClient source, RedissonClient dest, String key) {
        if (StringUtils.isBlank(key)) {
            return;
        }

        RType rType = source.getKeys().getType(key);
        log.info("migration key={} type={}",key, rType);
        if (RType.OBJECT.equals(rType)) {
            migrationObject(source, dest, key);
            return;
        }

        if (RType.MAP.equals(rType)) {
            migrationHash(source, dest, key);
            return;
        }

        if (RType.LIST.equals(rType)) {
            migrationList(source, dest, key);
            return;
        }

        if (RType.SET.equals(rType)) {
            migrationSet(source, dest, key);
            return;
        }

        if (RType.ZSET.equals(rType)) {
            migrationZSet(source, dest, key);
            return;
        }
    }

    private static void migrationObject(RedissonClient source, RedissonClient dest, String key) {
        RBucket bucket = source.getBucket(key, StringCodec.INSTANCE);
        long ttl = bucket.remainTimeToLive();
        if (ttl == -2L || ttl == 0L) {
            return;
        }

        Object value = bucket.get();
        if (value == null) {
            return;
        }

        if (ttl == -1L) {
            dest.getBucket(key, StringCodec.INSTANCE).set(value);
        } else {
            dest.getBucket(key, StringCodec.INSTANCE).set(value, ttl, TimeUnit.MILLISECONDS);
        }
    }

    private static void migrationHash(RedissonClient source, RedissonClient dest, String key) {
        RMap map = source.getMap(key, StringCodec.INSTANCE);
        long ttl = map.remainTimeToLive();
        if (ttl == -2L || ttl == 0L) {
            return;
        }

        RMap destMap = dest.getMap(key, StringCodec.INSTANCE);
        if (ttl > 0L) {
            destMap.expire(ttl, TimeUnit.MILLISECONDS);
        }

        destMap.putAll(map);
    }


    private static void migrationList(RedissonClient source, RedissonClient dest, String key) {
        RList list = source.getList(key, StringCodec.INSTANCE);
        long ttl = list.remainTimeToLive();
        if (ttl == -2L || ttl == 0L) {
            return;
        }

        RList destList = dest.getList(key, StringCodec.INSTANCE);
        if (ttl > 0L) {
            destList.expire(ttl, TimeUnit.MILLISECONDS);
        }

        destList.addAll(list);
    }

    private static void migrationSet(RedissonClient source, RedissonClient dest, String key) {
        RSet set = source.getSet(key, StringCodec.INSTANCE);
        long ttl = set.remainTimeToLive();
        if (ttl == -2L || ttl == 0L) {
            return;
        }

        RSet destSet = dest.getSet(key, StringCodec.INSTANCE);
        if (ttl > 0L) {
            destSet.expire(ttl, TimeUnit.MILLISECONDS);
        }

        destSet.addAll(set);
    }

    private static void migrationZSet(RedissonClient source, RedissonClient dest, String key) {
        RScoredSortedSet<Object> set = source.getScoredSortedSet(key, StringCodec.INSTANCE);
        long ttl = set.remainTimeToLive();
        if (ttl == -2L || ttl == 0L) {
            return;
        }

        RScoredSortedSet<Object> destSet = dest.getScoredSortedSet(key, StringCodec.INSTANCE);
        if (ttl > 0L) {
            destSet.expire(ttl, TimeUnit.MILLISECONDS);
        }

        set.entryRange(0, -1).forEach(entry -> destSet.add(entry.getScore(), entry.getValue()));
    }
}
