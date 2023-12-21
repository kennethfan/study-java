package io.github.kennethfan.netty.file.client.action;

import io.github.kennethfan.netty.file.common.command.UploadCommand;
import io.github.kennethfan.netty.file.common.enums.CodecEnum;
import io.github.kennethfan.netty.file.common.protocol.Request;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

@Slf4j
public class UploadAction implements Action {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        UploadCommand command = new UploadCommand()
                .setBasedir(scanner.next())
                .setFilename(scanner.next());

        String fullPath = Paths.get(command.getFilename()).toAbsolutePath().toString();
        File file = new File(fullPath);
        if (!file.exists()) {
            log.error("{} not exists", fullPath);
            return;
        }

        if (!file.isFile()) {
            log.error("{} not a file", fullPath);
            return;
        }

        try {
            try (FileInputStream inputStream = new FileInputStream(file)) {
                byte[] bytes = new byte[inputStream.available()];
                inputStream.read(bytes);
                command.setData(bytes);
            }
        } catch (IOException e) {
            log.error("upload error, path={}", fullPath, e);
            return;
        }

        log.info("upload.exec. basedir={}, filename", command.getBasedir(), command.getFilename());
        Request request = new Request()
                .setCodec(CodecEnum.JSON.getCodec())
                .setCommand(command);

        // 发送登录数据包
        channel.writeAndFlush(request);
    }
}
