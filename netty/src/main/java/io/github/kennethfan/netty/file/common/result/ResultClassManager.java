package io.github.kennethfan.netty.file.common.result;

import io.github.kennethfan.netty.file.common.enums.ResultTypeEnum;
import io.github.kennethfan.netty.file.common.exception.UnsupportResultException;

import java.util.HashMap;
import java.util.Map;

public class ResultClassManager {

    private static final Map<Integer, Class<? extends Result>> RESULT_CLASS_MAP;

    static {
        RESULT_CLASS_MAP = new HashMap<>();

        RESULT_CLASS_MAP.put(ResultTypeEnum.NO_AUTH.getType(), NoAuthResult.class);
        RESULT_CLASS_MAP.put(ResultTypeEnum.AUTH_FAILED.getType(), AuthFailedResult.class);
        RESULT_CLASS_MAP.put(ResultTypeEnum.AUTH_SUCCESS.getType(), AuthSuccessResult.class);

        RESULT_CLASS_MAP.put(ResultTypeEnum.NOT_DIR.getType(), NotDirectoryResult.class);
        RESULT_CLASS_MAP.put(ResultTypeEnum.FILE_EXISTS.getType(), FileExistsResult.class);
        RESULT_CLASS_MAP.put(ResultTypeEnum.FILE_NOT_EXISTS.getType(), FileNotExistsResult.class);
        RESULT_CLASS_MAP.put(ResultTypeEnum.FILE_LIST.getType(), ListResult.class);
        RESULT_CLASS_MAP.put(ResultTypeEnum.UPLOAD_SUCCESS.getType(), UploadSuccessResult.class);
        RESULT_CLASS_MAP.put(ResultTypeEnum.DOWNLOAD_SUCCESS.getType(), DownloadSuccessResult.class);

        RESULT_CLASS_MAP.put(ResultTypeEnum.HEART_BEAT.getType(), HeartBeatResult.class);
    }

    public static Class<? extends Result> getCommandClass(int resultType) {
        Class<? extends Result> clazz = RESULT_CLASS_MAP.get(resultType);
        if (clazz == null) {
            throw new UnsupportResultException(resultType);
        }

        return clazz;
    }
}
