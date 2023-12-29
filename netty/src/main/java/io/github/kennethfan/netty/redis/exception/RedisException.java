package io.github.kennethfan.netty.redis.exception;

public class RedisException extends RuntimeException {

    public RedisException(Throwable cause) {
        super(cause);
    }


    public RedisException(String message, Throwable cause) {
        super(message, cause);
    }
}

