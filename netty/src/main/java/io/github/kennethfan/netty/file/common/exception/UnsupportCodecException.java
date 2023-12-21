package io.github.kennethfan.netty.file.common.exception;

public class UnsupportCodecException extends RuntimeException {

    public UnsupportCodecException(int codecType) {
        super("unsupport codec type " + codecType);
    }
}
