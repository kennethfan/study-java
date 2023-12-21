package io.github.kennethfan.netty.file.common.enums;

import lombok.Getter;

@Getter
public enum CodecEnum {

    /**
     * JSON codec
     */
    JSON(1),
    ;

    private int codec;

    CodecEnum(int codec) {
        this.codec = codec;
    }
}
