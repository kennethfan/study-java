package io.github.kennethfan.netty.redis.config;

import io.github.kennethfan.netty.redis.pool.RedisPoolConfig;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Setter
@Getter
@ToString
public class RedisConfig {

    private String ip;

    private int port = 6379;

    private String password;

    private int database = 0;

    private RedisPoolConfig poolConfig;
}
