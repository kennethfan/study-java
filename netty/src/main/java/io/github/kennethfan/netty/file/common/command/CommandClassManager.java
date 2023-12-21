package io.github.kennethfan.netty.file.common.command;

import io.github.kennethfan.netty.file.common.enums.CommandTypeEnum;
import io.github.kennethfan.netty.file.common.exception.UnsupportCommandException;

import java.util.HashMap;
import java.util.Map;

public class CommandClassManager {

    private static final Map<Integer, Class<? extends Command>> COMMAND_CLASS_MAP;

    static {
        COMMAND_CLASS_MAP = new HashMap<>();

        COMMAND_CLASS_MAP.put(CommandTypeEnum.LOGIN.getType(), LoginCommand.class);
        COMMAND_CLASS_MAP.put(CommandTypeEnum.LIST.getType(), ListCommand.class);
        COMMAND_CLASS_MAP.put(CommandTypeEnum.UPLOAD.getType(), UploadCommand.class);
        COMMAND_CLASS_MAP.put(CommandTypeEnum.DOWNLOAD.getType(), DownloadCommand.class);
        COMMAND_CLASS_MAP.put(CommandTypeEnum.HEART_BEAT.getType(), HeartBeatCommand.class);
    }

    public static Class<? extends Command> getCommandClass(int commandType) {
        Class<? extends Command> clazz = COMMAND_CLASS_MAP.get(commandType);
        if (clazz == null) {
            throw new UnsupportCommandException(commandType);
        }

        return clazz;
    }
}
