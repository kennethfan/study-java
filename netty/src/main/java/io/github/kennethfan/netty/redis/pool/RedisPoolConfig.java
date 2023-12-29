package io.github.kennethfan.netty.redis.pool;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Setter
@Getter
public class RedisPoolConfig {
    private int minIdle = 2;

    private int maxIdle = Runtime.getRuntime().availableProcessors() * 2;

    private long maxWaitMills = 3000L;

    private boolean testOnBorrow = true;

    private boolean testOnReturn = true;
}
