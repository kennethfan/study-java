package io.github.kennethfan.netty.file.common.enums;

import lombok.Getter;

@Getter
public enum ResultTypeEnum {

    /**
     * 未授权
     */
    NO_AUTH(0x01),

    /**
     * 授权失败
     */
    AUTH_FAILED(0x02),

    /**
     * 授权成功
     */
    AUTH_SUCCESS(0x03),

    /**
     * 不是目录
     */
    NOT_DIR(0x04),

    /**
     * 文件已存在
     */
    FILE_EXISTS(0x05),

    /**
     * 文件不存在
     */
    FILE_NOT_EXISTS(0x06),

    /**
     * 文件列表
     */
    FILE_LIST(0x07),

    /**
     * 上传成功
     */
    UPLOAD_SUCCESS(0x08),

    /**
     * 文件内容
     */
    DOWNLOAD_SUCCESS(0x09),

    /**
     * 心跳
     */
    HEART_BEAT(0x7fff);

    private int type;

    ResultTypeEnum(int type) {
        this.type = type;
    }
}
