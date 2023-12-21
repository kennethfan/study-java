package io.github.kennethfan.netty.file.common.result;

import io.github.kennethfan.netty.file.common.enums.ResultTypeEnum;

public class HeartBeatResult extends Result {

    @Override
    public ResultTypeEnum resultType() {
        return ResultTypeEnum.HEART_BEAT;
    }
}
