package io.github.kennethfan.netty.file.common.codec;

import io.github.kennethfan.netty.file.common.enums.CodecEnum;
import io.github.kennethfan.netty.file.common.exception.UnsupportCodecException;

import java.util.HashMap;
import java.util.Map;

public class CodecManager {

    private static final Map<Integer, ICodec> codecMap;

    static {
        codecMap = new HashMap<>();
        codecMap.put(CodecEnum.JSON.getCodec(), JsonCodec.getInstance());
    }

    private CodecManager() {
    }

    public static ICodec getCodec(int codecType) {
        ICodec codec = codecMap.get(codecType);
        if (codec == null) {
            throw new UnsupportCodecException(codecType);
        }

        return codec;
    }
}
