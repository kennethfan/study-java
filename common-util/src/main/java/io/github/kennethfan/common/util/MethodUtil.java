package io.github.kennethfan.common.util;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created by kenneth on 2023/5/28.
 */
public class MethodUtil {

    /**
     * 是否interface default方法
     *
     * @param method
     * @return
     */
    public static boolean isDefaultMethod(Method method) {
        return method.getDeclaringClass().isInterface()
                && (method.getModifiers() & (Modifier.ABSTRACT | Modifier.PUBLIC | Modifier.STATIC)) == Modifier.PUBLIC;
    }

    /**
     * 调用default方法
     *
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    public static Object invokeDefaultMethod(Object proxy, Method method, Object[] args) throws Throwable {
        final Constructor<MethodHandles.Lookup> constructor = MethodHandles.Lookup.class.getDeclaredConstructor(Class.class, int.class);
        if (!constructor.isAccessible()) {
            constructor.setAccessible(true);
        }

        final Class<?> clazz = method.getDeclaringClass();
        return constructor.newInstance(clazz, MethodHandles.Lookup.PACKAGE | MethodHandles.Lookup.PUBLIC | MethodHandles.Lookup.PROTECTED | MethodHandles.Lookup.PRIVATE)
                .unreflectSpecial(method, clazz)
                .bindTo(proxy)
                .invokeWithArguments(args);
    }
}
