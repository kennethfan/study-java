package io.github.kennethfan.algorithm;

public class SpecialKeyboard {

    public static void main(String[] args) {

    }

    /**
     * 假设你有一个特殊的键盘包含下面的按键：
     * Key1: (A)：在屏幕上打印一个A。
     * Key2: (Ctrl-A)：选中整个屏幕。
     * Key3: (Ctrl-C)：复制选中区域到缓冲区。
     * Key4: (Ctrl-V)：将缓冲区内容输出到上次输入的结束位置，并显示在屏幕上。
     * 现在，你只可以按键N次（使用上述四种按键），请问屏幕上最多可以显示几个A呢？
     * 样例1：
     * 输入：N=3
     * 输出：3
     * 解释：
     * 我们最多可以在屏幕上显示三个，A通过如下顺序按键：
     * A, A, A
     * 样例2：
     * 输入：N=7
     * 输出：9
     * 解释：
     * 我们最多可以在屏幕上显示九个A通过如下顺序按键：
     * A, A, A, Ctrl A, Ctrl C, Ctrl V, Ctrl V
     *
     * @param n
     * @return
     */
    private static int maxNum(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        if (n == 3) {
            return 3;
        }
        if (n == 4) {
//            max(A CA CC CV, AAAA)
        }
        if (n == 5) {
//            max(AAAAA, A CA CC CV CV, AA CA CC CV)
        }
        if (n == 6) {
//            max(AAAAA，  A CA CC CV CV
        }

        return 0;
    }

    private static int maxNumLastA(int n) {

    }

    private static int maxNumLastC(int n) {

    }

    private static int maxNumLastV(int n) {

    }
}
