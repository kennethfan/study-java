package io.github.kennethfan.netty.file.common.command;

import io.github.kennethfan.netty.file.common.enums.CommandTypeEnum;

public abstract class Command {

    /**
     * @return
     */
    public abstract CommandTypeEnum type();
}
