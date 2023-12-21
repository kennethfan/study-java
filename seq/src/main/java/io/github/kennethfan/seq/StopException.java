package io.github.kennethfan.seq;

/**
 * Created by kenneth on 2023/5/30.
 */
public class StopException extends RuntimeException {
    public static final StopException INSTANCE = new StopException();

    private StopException() {

    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
