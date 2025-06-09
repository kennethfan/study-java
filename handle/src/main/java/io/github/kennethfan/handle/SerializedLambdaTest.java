package io.github.kennethfan.handle;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.Locale;

@Slf4j
public class SerializedLambdaTest {

    public static <T> String pn(SFunction<T, Object> parseFun) {
        Object sl = null;
        try {
            Method writeReplace = parseFun.getClass().getDeclaredMethod("writeReplace");
            writeReplace.setAccessible(true);
            sl = writeReplace.invoke(parseFun);
        } catch (Exception e) {
            throw new RuntimeException("parseColName error", e);
        }
        SerializedLambda serializedLambda = (SerializedLambda) sl;
        String className = serializedLambda.getImplClass();
        className = className.replace("/", ".");
        String methodName = serializedLambda.getImplMethodName();
        if (methodName.startsWith("is")) {
            methodName = methodName.substring(2);
        } else {
            if (!methodName.startsWith("get") && !methodName.startsWith("set")) {
                throw new RuntimeException("Error parsing property name '" + methodName
                        + "'.  Didn't start with 'is', 'get' or 'set'.");
            }
            methodName = methodName.substring(3);
        }
        if (methodName.length() == 1 || (methodName.length() > 1 && !Character
                .isUpperCase(methodName.charAt(1)))) {
            methodName = methodName.substring(0, 1).toLowerCase(Locale.ENGLISH) + methodName.substring(1);
        }

        log.info("class={}, methodName={}", className, methodName);
        return className + "." + methodName;
    }

    public static void main(String[] args) {
        pn(User::getName);
        pn(User::getAge);
    }


    @Getter
    @Setter
    static class User {
        private String name;
        private Integer age;
    }

}
