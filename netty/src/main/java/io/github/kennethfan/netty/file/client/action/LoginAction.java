package io.github.kennethfan.netty.file.client.action;

import io.github.kennethfan.netty.file.common.command.LoginCommand;
import io.github.kennethfan.netty.file.common.enums.CodecEnum;
import io.github.kennethfan.netty.file.common.protocol.Request;
import io.netty.channel.Channel;

import java.util.Scanner;

public class LoginAction implements Action {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        LoginCommand command = new LoginCommand();

        System.out.print("输入用户名登录: ");
        command.setUsername(scanner.nextLine());
        command.setPasswd("123456");

        Request request = new Request()
                .setCodec(CodecEnum.JSON.getCodec())
                .setCommand(command);

        // 发送登录数据包
        channel.writeAndFlush(request);
        waitForLoginResponse();
    }

    private static void waitForLoginResponse() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }
}
