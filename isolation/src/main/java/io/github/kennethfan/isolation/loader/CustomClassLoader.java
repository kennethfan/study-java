package io.github.kennethfan.isolation.loader;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by kenneth on 2023/5/28.
 */
public class CustomClassLoader extends URLClassLoader {
    /**
     * class缓存
     */
    private Map<String, Class<?>> classesCache = new ConcurrentHashMap<>();

    public CustomClassLoader(URL[] urls) {
        super(urls);
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        return this.findClass(name);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        if (name.startsWith("java.")
                || name.startsWith("javax.")
                || name.startsWith("sun.")) {
            return getClass().getClassLoader().loadClass(name);
        }

        Class<?> clazz = classesCache.computeIfAbsent(name, className -> {
            try {
                return CustomClassLoader.super.findClass(className);
            } catch (ClassNotFoundException e) {
                return null;
            }
        });
        if (clazz != null) {
            return clazz;
        }

        return getClass().getClassLoader().loadClass(name);
    }
}
