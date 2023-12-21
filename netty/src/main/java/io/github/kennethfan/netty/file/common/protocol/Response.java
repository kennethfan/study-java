package io.github.kennethfan.netty.file.common.protocol;

import io.github.kennethfan.netty.file.common.result.Result;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Getter
public class Response {

    private int version = 0x01;

    @Setter
    private int codec;

    @Setter
    private Result result;

    public Response() {
    }

    public Response(int version) {
        this.version = version;
    }
}
