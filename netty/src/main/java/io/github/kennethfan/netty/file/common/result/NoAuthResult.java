package io.github.kennethfan.netty.file.common.result;

import io.github.kennethfan.netty.file.common.enums.ResultTypeEnum;

public class NoAuthResult extends Result {

    private static final NoAuthResult INSTANCE = new NoAuthResult();

    private NoAuthResult() {
    }

    @Override
    public ResultTypeEnum resultType() {
        return ResultTypeEnum.NO_AUTH;
    }

    public static NoAuthResult getInstance() {
        return INSTANCE;
    }
}
