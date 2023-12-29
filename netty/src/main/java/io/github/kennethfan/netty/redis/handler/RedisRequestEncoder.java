package io.github.kennethfan.netty.redis.handler;

import io.github.kennethfan.netty.redis.protocol.command.RedisCommand;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static io.github.kennethfan.netty.redis.protocol.RedisFlag.*;

@Slf4j
public class RedisRequestEncoder extends MessageToByteEncoder<RedisCommand> {

    @Override
    protected void encode(ChannelHandlerContext ctx, RedisCommand msg, ByteBuf out) throws Exception {
        log.debug("RedisRequestEncoder.encode msg={}", msg);
        if (msg == null) {
            ctx.writeAndFlush(msg);
            return;
        }

        List<String> args = msg.cmd();
        if (CollectionUtils.isEmpty(args)) {
            ctx.writeAndFlush(msg);
            return;
        }

        // 写入数组标识
        out.writeByte(ARRAY_FLAG);
        // 数组长度
        out.writeCharSequence(String.valueOf(args.size()), CharsetUtil.US_ASCII);
        out.writeByte(CR_FLAG);
        out.writeByte(LF_FLAG);

        for (String arg : args) {
            // 字符串标识
            out.writeByte(MULTI_STRING_FLAG);
            if (arg == null) {
                out.writeCharSequence(String.valueOf(NULL_STR_LENGTH), CharsetUtil.US_ASCII);
                out.writeByte(CR_FLAG);
                out.writeByte(LF_FLAG);
                continue;
            }

            byte[] bytes = arg.getBytes(StandardCharsets.UTF_8);
            out.writeCharSequence(String.valueOf(bytes.length), CharsetUtil.US_ASCII);
            out.writeByte(CR_FLAG);
            out.writeByte(LF_FLAG);

            out.writeBytes(bytes);
            out.writeByte(CR_FLAG);
            out.writeByte(LF_FLAG);
        }
    }
}
