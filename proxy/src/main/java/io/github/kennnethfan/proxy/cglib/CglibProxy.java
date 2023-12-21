package io.github.kennnethfan.proxy.cglib;

import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by kenneth on 2023/5/28.
 */
public class CglibProxy implements MethodInterceptor {
    private static final CglibProxy HOLDER = new CglibProxy();

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        // before
        Object result = null;
        try {
            result = proxy.invokeSuper(obj, args);
            // after
        } catch (Exception throwable) {
            // exception
        }

        return result;
    }

    public static <T> T createProxy(T target) {
        if (target == null) {
            throw new IllegalArgumentException();
        }

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setUseCache(false);
        enhancer.setCallback(HOLDER);
        T proxy = (T) enhancer.create();
        BeanCopier.create(target.getClass(), target.getClass(), false)
                .copy(target, proxy, null);

        return proxy;
    }
}
