package io.github.kennethfan.netty.file.common.exception;

public class UnsupportCommandException extends RuntimeException {

    public UnsupportCommandException(int commandType) {
        super("unsupport command type " + commandType);
    }
}
