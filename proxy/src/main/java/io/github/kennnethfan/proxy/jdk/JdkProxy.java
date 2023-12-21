package io.github.kennnethfan.proxy.jdk;


import io.github.kennethfan.common.util.MethodUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by kenneth on 2023/5/28.
 */
public class JdkProxy<T> implements InvocationHandler {

    private T target;

    public JdkProxy(T target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this, args);
        }

        if (MethodUtil.isDefaultMethod(method)) {
            return MethodUtil.invokeDefaultMethod(proxy, method, args);
        }

        // before
        Object result = null;
        try {
            result = method.invoke(this.target, args);
            // after
        } catch (Exception e) {
            // exception
        }

        return result;
    }

    public static <T> T newProxy(T target) {
        return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new JdkProxy<>(target));
    }
}
