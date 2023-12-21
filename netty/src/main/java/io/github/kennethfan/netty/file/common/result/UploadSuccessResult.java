package io.github.kennethfan.netty.file.common.result;

import io.github.kennethfan.netty.file.common.enums.ResultTypeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Getter
@Setter
public class UploadSuccessResult extends Result {

    private String basedir;

    private String filename;

    @Override
    public ResultTypeEnum resultType() {
        return ResultTypeEnum.UPLOAD_SUCCESS;
    }
}
