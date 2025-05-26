package io.github.kennethfan.handle;

import lombok.extern.slf4j.Slf4j;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

@Slf4j
public class MethodHandleTest {


    public static void main(String[] args) throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle mh = lookup.findVirtual(String.class, "length", MethodType.methodType(int.class));
        int length = (int) mh.invokeExact("Hello");
        log.info("length={}", length);
    }
}
