package io.github.kennethfan.od.star1;

public class N6 {


    public static void main(String[] args) {
        String before = "abcd";
        System.out.println("before=" + before);
        System.out.println("afeter=" + transfer(before));

        before = "azfdsajlfdsajflkdfdsafadsjlk";
        System.out.println("before=" + before);
        System.out.println("afeter=" + transfer(before));

        before = "azfdsjlfdsjflkdfdsfdsjlka";
        System.out.println("before=" + before);
        System.out.println("afeter=" + transfer(before));

        before = "zzfdsjlfdsjflkdfdsfdsjlka";
        System.out.println("before=" + before);
        System.out.println("afeter=" + transfer(before));
    }

    /***
     * 给定一个字符串s，最多只能进行一次变换，返回变换后能得到的最小字符串（按照字典序进行比较）。
     * 变换规则：交换字符串中任意两个不同位置的字符。
     * @param input
     * @return
     */
    public static String transfer(String input) {
        if (input == null || input.length() == 0) {
            return input;
        }

        final int length = input.length();

        for (int i = 0; i < length - 1; i++) {
            if (input.charAt(i) > input.charAt(i + 1)) {
                char min = input.charAt(i + 1);
                int pos = i + 1;
                for (int j = i + 2; j < length; j++) {
                    if (input.charAt(j) <= min) {
                        min = input.charAt(j);
                        pos = j;
                    }
                }

                for (int k = 0; k <= i; k++) {
                    if (input.charAt(k) == input.charAt(i)) {
                        return input.substring(0, k) + min + input.substring(k + 1, pos) + input.charAt(k) + input.substring(pos + 1);
                    }
                }

            }
        }

        return input;
    }
}
