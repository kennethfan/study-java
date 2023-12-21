package io.github.kennethfan.netty.file.common.enums;

import lombok.Getter;

@Getter
public enum CommandTypeEnum {
    /**
     * 登录
     */
    LOGIN(0x01),

    /**
     * 列出当前目录下所有文件
     */
    LIST(0x02),

    /**
     * 上传
     */
    UPLOAD(0x03),

    /**
     * 下载
     */
    DOWNLOAD(0x04),

    /**
     * 心跳
     */
    HEART_BEAT(0x7fff);

    private int type;

    CommandTypeEnum(int type) {
        this.type = type;
    }
}
