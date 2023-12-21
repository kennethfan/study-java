package io.github.kennethfan.netty.file.common.session;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

@Accessors(chain = true)
@Getter
public class Session {
    private static final AttributeKey<Session> SESSION = AttributeKey.newInstance("session");

    private String id = UUID.randomUUID().toString().replaceAll("-", "");

    private Channel channel;

    @Setter
    private String username;

    private Session(Channel channel) {
        this.channel = channel;
    }

    public void clear() {
        this.channel.attr(SESSION).set(null);
    }

    public static Session get(Channel channel) {
        return channel.attr(SESSION).get();
    }

    public static void put(Channel channel, String username) {
        channel.attr(SESSION).set(new Session(channel).setUsername(username));
    }
}
