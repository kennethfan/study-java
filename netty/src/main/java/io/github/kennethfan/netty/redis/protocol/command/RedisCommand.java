package io.github.kennethfan.netty.redis.protocol.command;

import java.util.List;

public interface RedisCommand {

    List<String> cmd();

    String AUTH = "AUTH";

    String SELECT = "SELECT";
}
