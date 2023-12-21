package io.github.kennethfan.anno.annos;

import java.lang.annotation.*;

/**
 * Created by kenneth on 2023/5/28.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BaseAnno {
    /**
     * @return
     */
    String value() default "";
}
