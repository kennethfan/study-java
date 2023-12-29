package io.github.kennethfan.netty.redis.protocol.command.base;

import com.google.common.collect.Lists;
import io.github.kennethfan.netty.redis.protocol.command.RedisCommand;
import lombok.ToString;

import java.util.List;

@ToString
public class AuthCommand implements RedisCommand {

    private String password;

    private AuthCommand(String password) {
        this.password = password;
    }

    @Override
    public List<String> cmd() {
        return Lists.newArrayList(AUTH, this.password);
    }

    public static AuthCommand newInstance(String password) {
        return new AuthCommand(password);
    }
}
