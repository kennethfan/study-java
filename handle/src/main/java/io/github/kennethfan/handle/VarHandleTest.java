package io.github.kennethfan.handle;

import lombok.extern.slf4j.Slf4j;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

@Slf4j
public class VarHandleTest {

    private volatile int value;


    public static void main(String[] args) throws Exception {
        VarHandleTest obj = new VarHandleTest();
        VarHandle vh = MethodHandles.lookup().findVarHandle(VarHandleTest.class, "value", int.class);
        vh.set(obj, 42);
        log.info("value: {}", vh.get(obj));
    }
}
