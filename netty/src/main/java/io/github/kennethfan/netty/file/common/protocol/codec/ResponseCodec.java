package io.github.kennethfan.netty.file.common.protocol.codec;

import io.github.kennethfan.netty.file.common.codec.CodecManager;
import io.github.kennethfan.netty.file.common.codec.ICodec;
import io.github.kennethfan.netty.file.common.protocol.Response;
import io.github.kennethfan.netty.file.common.result.Result;
import io.github.kennethfan.netty.file.common.result.ResultClassManager;
import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResponseCodec {

    public static final int MAGIC_NUMBER = Integer.MAX_VALUE;

    private static final ResponseCodec INSTANCE = new ResponseCodec();

    private ResponseCodec() {
    }

    public void encode(ByteBuf byteBuf, Response response) {
        ICodec codec = CodecManager.getCodec(response.getCodec());

        // 1. 序列化 java 对象
        byte[] bytes = codec.encode(response.getResult());

        // 2. 实际编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeInt(response.getVersion());
        byteBuf.writeInt(response.getCodec());
        byteBuf.writeInt(response.getResult().resultType().getType());
        byteBuf.writeInt(bytes.length);
        if (bytes.length > 0) {
            byteBuf.writeBytes(bytes);
        }
    }


    public Result decode(ByteBuf byteBuf) {
        // magic number
        byteBuf.readInt();

        // version;
        int version = byteBuf.readInt();

        int codecType = byteBuf.readInt();
        ICodec codec = CodecManager.getCodec(codecType);

        Class<? extends Result> cmdClazz = ResultClassManager.getCommandClass(byteBuf.readInt());

        Result result = null;
        int length = byteBuf.readInt();
        if (length > 0) {
            byte[] bytes = new byte[length];
            byteBuf.readBytes(bytes);

            result = codec.decode(bytes, cmdClazz);
        }

        log.info("ResponseCodec.decode, result={}", result);
        return result;
    }

    public static ResponseCodec getInstance() {
        return INSTANCE;
    }

}
