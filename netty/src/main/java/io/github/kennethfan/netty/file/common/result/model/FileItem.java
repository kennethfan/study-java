package io.github.kennethfan.netty.file.common.result.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Setter
@Getter
public class FileItem {

    private String name;

    private String type;

    private long size;
}
