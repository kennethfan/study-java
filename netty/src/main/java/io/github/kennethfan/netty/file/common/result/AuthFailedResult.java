package io.github.kennethfan.netty.file.common.result;

import io.github.kennethfan.netty.file.common.enums.ResultTypeEnum;

public class AuthFailedResult extends Result {

    private static final AuthFailedResult INSTANCE = new AuthFailedResult();

    private AuthFailedResult() {
    }

    @Override
    public ResultTypeEnum resultType() {
        return ResultTypeEnum.AUTH_FAILED;
    }

    public static AuthFailedResult getInstance() {
        return INSTANCE;
    }
}
