package io.github.kennethfan.netty.file.common;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;


public class ChannelContext {

    private static final AttributeKey<String> USERNAME = AttributeKey.newInstance("username");


    public static void setUsername(Channel channel, String username) {
        channel.attr(USERNAME).set(username);
    }

    public static String getUsername(Channel channel) {
        return channel.attr(USERNAME).get();
    }
}
