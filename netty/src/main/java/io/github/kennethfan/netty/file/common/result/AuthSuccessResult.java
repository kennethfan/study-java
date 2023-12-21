package io.github.kennethfan.netty.file.common.result;

import io.github.kennethfan.netty.file.common.enums.ResultTypeEnum;

public class AuthSuccessResult extends Result {

    private static final AuthSuccessResult INSTANCE = new AuthSuccessResult();

    private AuthSuccessResult() {
    }

    @Override
    public ResultTypeEnum resultType() {
        return ResultTypeEnum.AUTH_SUCCESS;
    }

    public static AuthSuccessResult getInstance() {
        return INSTANCE;
    }
}
