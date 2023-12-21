package io.github.kennethfan.jnr;

import jnr.ffi.LibraryLoader;

public class JNRTest {
    public interface NativeLib {
        NativeLib INSTANCE = LibraryLoader.create(NativeLib.class).load("add.so");

        int add(int a, int b);
    }

    public static void main(String[] args) {
        NativeLib instance = NativeLib.INSTANCE;

        System.out.println(instance.add(1, 3));
    }
}
