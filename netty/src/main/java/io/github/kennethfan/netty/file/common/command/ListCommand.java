package io.github.kennethfan.netty.file.common.command;

import io.github.kennethfan.netty.file.common.enums.CommandTypeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Getter
@Setter
public class ListCommand extends Command {

    private String basedir;

    @Override
    public CommandTypeEnum type() {
        return CommandTypeEnum.LIST;
    }
}
