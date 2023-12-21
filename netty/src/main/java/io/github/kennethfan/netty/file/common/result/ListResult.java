package io.github.kennethfan.netty.file.common.result;

import io.github.kennethfan.netty.file.common.enums.ResultTypeEnum;
import io.github.kennethfan.netty.file.common.result.model.FileItem;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors(chain = true)
@Getter
@Setter
public class ListResult extends Result {

    private String basedir;

    private List<FileItem> fileItemList;

    @Override
    public ResultTypeEnum resultType() {
        return ResultTypeEnum.FILE_LIST;
    }
}
