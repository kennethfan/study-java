package io.github.kennethfan.netty.file.common.result;

import io.github.kennethfan.netty.file.common.enums.ResultTypeEnum;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NotDirectoryResult extends Result {

    private String basedir;

    private NotDirectoryResult() {
    }

    @Override
    public ResultTypeEnum resultType() {
        return ResultTypeEnum.NOT_DIR;
    }

    public static NotDirectoryResult newInstance(String basedir) {
        NotDirectoryResult result = new NotDirectoryResult();
        result.setBasedir(basedir);

        return result;
    }
}
