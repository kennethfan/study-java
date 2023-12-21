package io.github.kennethfan.netty.file.common.protocol;

import io.github.kennethfan.netty.file.common.command.Command;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Getter
public class Request {

    private int version = 0x01;

    @Setter
    private int codec;

    @Setter
    private Command command;

    public Request() {
    }

    public Request(int version) {
        this.version = version;
    }
}
