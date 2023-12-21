package io.github.kennethfan.netty.file.common;

import org.apache.commons.lang3.StringUtils;

public class PropertyUtil {

    private static final String SERVER_PORT_KEY = "netty.server.port";

    private static final int DEFAULT_SERVER_PORT = 9000;

    /**
     * 获取端口号
     *
     * @return
     */
    public static int getPort() {
        String portStr = System.getProperty(SERVER_PORT_KEY);
        return StringUtils.isBlank(portStr) ? DEFAULT_SERVER_PORT : Integer.parseInt(portStr);
    }
}
