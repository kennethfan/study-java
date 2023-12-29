package io.github.kennethfan.netty.redis;

import io.github.kennethfan.netty.redis.client.RedisClient;
import io.github.kennethfan.netty.redis.config.RedisConfig;
import io.github.kennethfan.netty.redis.pool.RedisPoolConfig;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class RedisMain {

    public static void main(String[] args) throws Exception {
        RedisConfig config = new RedisConfig()
                .setIp("127.0.0.1")
                .setPort(6379)
                .setPassword("123456")
//                .setPassword("12345678")
                .setDatabase(1)
                .setPoolConfig(new RedisPoolConfig());


        RedisClient redisClient = new RedisClient(config);
//
        redisClient.send("SET CCCCCC 中国人\r\n");
        log.info(redisClient.send("GET CCCCCC\r\n"));

        redisClient.send("SET DDDDDD 10086\r\n");
        redisClient.send("INCR DDDDDD\r\n");
        log.info(redisClient.send("GET DDDDDD\r\n"));

        redisClient.send("SET EEEEEE 10086.0001\r\n");
        log.info(redisClient.send("GET EEEEEE\r\n"));

        redisClient.send("PING\r\n");

        redisClient.send("LPUSH list a 1 23 b d\r\n");

        log.info("listData={}", (List) redisClient.send("LRANGE list 0 -1\r\n"));

        log.info("xxx={}", (String) redisClient.send("GET XXXXXXXXX\r\n"));
    }
}
