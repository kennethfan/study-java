package io.github.kennethfan.netty.redis.protocol;


public class RedisFlag {

    /**
     * 错误标识
     */
    public static final byte ERROR_FLAG = (byte) '-';
    /**
     * 简单字符串标识
     */
    public static final byte SIMPLE_STRING_FLAG = (byte) '+';
    /**
     * 字符串标识
     */
    public static final byte MULTI_STRING_FLAG = (byte) '$';
    /**
     * 整数标识
     */
    public static final byte NUMBER_FLAG = (byte) ':';
    /**
     * 数组标识
     */
    public static final byte ARRAY_FLAG = (byte) '*';

    /**
     * 回车
     */
    public static final byte CR_FLAG = (byte)  '\r';

    /**
     * 换行
     */
    public static final byte LF_FLAG = (byte)  '\n';


    public static final int NULL_STR_LENGTH = -1;
}
