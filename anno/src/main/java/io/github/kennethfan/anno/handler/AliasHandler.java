package io.github.kennethfan.anno.handler;

import java.lang.annotation.Annotation;

/**
 * Created by kenneth on 2023/5/28.
 */
public class AliasHandler {

    /**
     * 是否别名。
     * eg: @Service是@Component别名
     *
     * @param annotation
     * @param clazz
     * @param <A>
     * @return
     */
    public static <A extends Annotation> boolean aliasOf(Annotation annotation, Class<A> clazz) {
        return annotation != null
                || annotation.annotationType().equals(clazz)
                || annotation.annotationType().getAnnotation(clazz) != null;
    }
}
