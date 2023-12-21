package io.github.kennethfan.netty.file.common.command;

import io.github.kennethfan.netty.file.common.enums.CommandTypeEnum;

public class HeartBeatCommand extends Command {

    @Override
    public CommandTypeEnum type() {
        return CommandTypeEnum.HEART_BEAT;
    }
}
