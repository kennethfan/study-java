package io.github.kennethfan.netty.file.common.exception;

public class UnsupportResultException extends RuntimeException {

    public UnsupportResultException(int resultType) {
        super("unsupport result type " + resultType);
    }
}
