package io.github.kennethfan.netty.file.common.protocol.codec;

import io.github.kennethfan.netty.file.common.codec.CodecManager;
import io.github.kennethfan.netty.file.common.codec.ICodec;
import io.github.kennethfan.netty.file.common.command.Command;
import io.github.kennethfan.netty.file.common.command.CommandClassManager;
import io.github.kennethfan.netty.file.common.protocol.Request;
import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestCodec {

    public static final int MAGIC_NUMBER = Integer.MAX_VALUE;

    private static final RequestCodec INSTANCE = new RequestCodec();

    private RequestCodec() {
    }

    public void encode(ByteBuf byteBuf, Request request) {
        ICodec codec = CodecManager.getCodec(request.getCodec());

        // 1. 序列化 java 对象
        byte[] bytes = codec.encode(request.getCommand());

        // 2. 实际编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeInt(request.getVersion());
        byteBuf.writeInt(request.getCodec());
        byteBuf.writeInt(request.getCommand().type().getType());
        byteBuf.writeInt(bytes.length);
        if (bytes.length > 0) {
            byteBuf.writeBytes(bytes);
        }
    }


    public Command decode(ByteBuf byteBuf) {
        // magic number
        byteBuf.readInt();

        // version;
        int version = byteBuf.readInt();

        int codecType = byteBuf.readInt();
        ICodec codec = CodecManager.getCodec(codecType);

        Class<? extends Command> cmdClazz = CommandClassManager.getCommandClass(byteBuf.readInt());

        Command command = null;
        int length = byteBuf.readInt();
        if (length > 0) {
            byte[] bytes = new byte[length];
            byteBuf.readBytes(bytes);

            command = codec.decode(bytes, cmdClazz);
        }

        log.info("RequestCodec.decode, command={}", command);
        return command;
    }


    public static RequestCodec getInstance() {
        return INSTANCE;
    }

}
