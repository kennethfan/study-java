package io.github.kennethfan.netty.file.client.action;

import io.github.kennethfan.netty.file.common.command.ListCommand;
import io.github.kennethfan.netty.file.common.enums.CodecEnum;
import io.github.kennethfan.netty.file.common.protocol.Request;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class ListAction implements Action {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        ListCommand command = new ListCommand();
        command.setBasedir(scanner.next());

        log.info("list.exec. basedir={}", command.getBasedir());
        Request request = new Request()
                .setCodec(CodecEnum.JSON.getCodec())
                .setCommand(command);

        // 发送登录数据包
        channel.writeAndFlush(request);
    }
}
