package io.github.kennethfan.netty.file.common.command;

import io.github.kennethfan.netty.file.common.enums.CommandTypeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Getter
@Setter
public class UploadCommand extends Command {

    private String basedir;

    private String filename;

    private byte[] data;

    @Override
    public CommandTypeEnum type() {
        return CommandTypeEnum.UPLOAD;
    }
}
