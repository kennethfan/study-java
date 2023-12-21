package io.github.kennethfan.netty.file.common.codec;

public interface ICodec {

    /**
     * encode
     *
     * @param data
     * @return
     */
    byte[] encode(Object data);

    /**
     * decode
     *
     * @param bytes
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T decode(byte[] bytes, Class<T> clazz);
}
