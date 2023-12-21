package io.github.kennethfan.anno.handler;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by kenneth on 2023/5/28.
 */
public class ValueHandler {

    /**
     * 获取注解内容
     *
     * @param annotation
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Object getValue(Annotation annotation) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return getValue(annotation, "value");
    }

    /**
     * 获取注解内容
     *
     * @param annotation
     * @param methodName
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Object getValue(Annotation annotation, String methodName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (annotation == null || methodName == null) {
            return null;
        }

        Method method = annotation.annotationType().getMethod(methodName);

        return method.invoke(annotation);
    }
}
