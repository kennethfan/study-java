package io.github.kennethfan.seq;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenneth on 2023/7/11.
 */
public class JvmTest {

    public static void main(String[] args) {
        List<String> xList = new ArrayList<>();
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            String.valueOf("hello" + i).intern();
            if (i %10000==0) {
                System.gc();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
//        r();
//        System.out.println(sum(100));
//        System.out.println(sum(1000000));
    }

    public static void r() {
        r();
    }


    public static int sum(int n) {
        System.out.println(n);
        if (n == 1) {
            return 1;
        }
        return n + sum(n - 1);
    }
}
