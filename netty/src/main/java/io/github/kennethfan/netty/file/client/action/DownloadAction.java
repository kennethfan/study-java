package io.github.kennethfan.netty.file.client.action;

import io.github.kennethfan.netty.file.common.command.DownloadCommand;
import io.github.kennethfan.netty.file.common.enums.CodecEnum;
import io.github.kennethfan.netty.file.common.protocol.Request;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class DownloadAction implements Action {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        DownloadCommand command = new DownloadCommand()
                .setBasedir(scanner.next())
                .setFilename(scanner.next());

        log.info("download.exec. basedir={}, filename", command.getBasedir(), command.getFilename());
        Request request = new Request()
                .setCodec(CodecEnum.JSON.getCodec())
                .setCommand(command);

        // 发送登录数据包
        channel.writeAndFlush(request);
    }
}
