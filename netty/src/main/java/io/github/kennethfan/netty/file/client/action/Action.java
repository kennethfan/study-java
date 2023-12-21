package io.github.kennethfan.netty.file.client.action;

import io.netty.channel.Channel;

import java.util.Scanner;

public interface Action {
    void exec(Scanner scanner, Channel channel);
}
