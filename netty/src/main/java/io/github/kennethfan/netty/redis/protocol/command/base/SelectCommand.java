package io.github.kennethfan.netty.redis.protocol.command.base;

import com.google.common.collect.ImmutableList;
import io.github.kennethfan.netty.redis.protocol.command.RedisCommand;
import lombok.ToString;

import java.util.List;

@ToString
public class SelectCommand implements RedisCommand {

    private final int database;

    private SelectCommand(int database) {
        this.database = database;
    }

    @Override
    public List<String> cmd() {
        return ImmutableList.of(SELECT, String.valueOf(database));
    }

    public static SelectCommand newInstance(int database) {
        return new SelectCommand(database);
    }
}
