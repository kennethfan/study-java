package io.github.kennethfan.netty.file.common.result;

import io.github.kennethfan.netty.file.common.enums.ResultTypeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Getter
@Setter
public class DownloadSuccessResult extends Result {

    private String basedir;

    private String filename;

    private byte[] data;

    @Override
    public ResultTypeEnum resultType() {
        return ResultTypeEnum.DOWNLOAD_SUCCESS;
    }
}
