package io.github.kennethfan.netty.file.client;

import io.github.kennethfan.netty.file.client.action.DownloadAction;
import io.github.kennethfan.netty.file.client.action.ListAction;
import io.github.kennethfan.netty.file.client.action.LoginAction;
import io.github.kennethfan.netty.file.client.action.UploadAction;
import io.github.kennethfan.netty.file.client.handler.*;
import io.github.kennethfan.netty.file.common.PropertyUtil;
import io.github.kennethfan.netty.file.common.handler.IdleStateHandler;
import io.github.kennethfan.netty.file.common.session.Session;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Scanner;

@Slf4j
public class NettyClient {
    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();

        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) {
                        ch.pipeline().addLast(IdleStateHandler.getInstance());
                        ch.pipeline().addLast(ResponseSplitor.newInstance());
                        ch.pipeline().addLast(ResponseDecoder.getInstance());
                        ch.pipeline().addLast(RequestEncoder.getInstance());
                        ch.pipeline().addLast(NoAuthHandler.getInstance());
                        ch.pipeline().addLast(AuthFailedHandler.getInstance());
                        ch.pipeline().addLast(AuthSuccessHandler.getInstance());
                        ch.pipeline().addLast(NotDirectoryHandler.getInstance());
                        ch.pipeline().addLast(FileExistsHandler.getInstance());
                        ch.pipeline().addLast(FileNotExistsHandler.getInstance());
                        ch.pipeline().addLast(ListSuccessHandler.getInstance());
                        ch.pipeline().addLast(UploadSuccessHandler.getInstance());
                        ch.pipeline().addLast(DownloadSuccessHandler.getInstance());
                        ch.pipeline().addLast(HeartBeatHandler.getInstance());
                    }
                });

        bootstrap.connect("127.0.0.1", PropertyUtil.getPort())
                .addListener(future -> {
                    if (future.isSuccess()) {
                        log.info("连接服务端成功");
                        Channel channel = ((ChannelFuture) future).channel();
                        // 连接之后，假设再这里发起各种操作指令，采用异步线程开始发送各种指令，发送数据用到的的channel是必不可少的
                        sendActionCommand(channel);
                    } else {
                        log.error("连接服务端失败");
                    }
                });
    }

    private static void sendActionCommand(Channel channel) {
//        // 直接采用控制台输入的方式，模拟操作指令
        Scanner scanner = new Scanner(System.in);
        LoginAction loginAction = new LoginAction();
        ListAction listAction = new ListAction();
        UploadAction uploadAction = new UploadAction();
        DownloadAction downloadAction = new DownloadAction();
        new Thread(() -> {
            // 此处循环等待客户端输入
            while (!Thread.interrupted()) {
                // 如果没登录，则直接登录
                if (Session.get(channel) == null) {
                    loginAction.exec(scanner, channel);
                } else {
                    //  登录后，后去获取指令
                    String command = scanner.next();
                    Session session = Session.get(channel);
                    log.info("session={}", session);
                    if (session == null) {
                        return;
                    }

                    // 如果是点对点发送消息
                    if (StringUtils.equalsIgnoreCase("list", command)) {
                        listAction.exec(scanner, channel);
                    } else if (StringUtils.equalsIgnoreCase("upload", command)) {
                        uploadAction.exec(scanner, channel);
                    } else if (StringUtils.equalsIgnoreCase("download", command)) {
                        downloadAction.exec(scanner, channel);
                    } else {
                        log.error("无法识别[{}]指令，请重新输入!", command);
                    }
                }
            }
        }).start();
    }
}
