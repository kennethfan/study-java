package io.github.kennethfan.od.star1;

public class N5 {

    public static void main(String[] args) {
        System.out.println(maxLength("ooo"));
        System.out.println(maxLength("ooooxoo"));
        System.out.println(maxLength("oxxxxxxxxoooxoo"));
        System.out.println(maxLength("oxxxxxxxxoooxoo"));
        System.out.println(maxLength("oxxxxxxxxoooxoo"));
        System.out.println(maxLength("oxxxxxxxxoooxoo"));
    }

    /**
     * 给你一个字符串 s，字符串s首尾相连成一个环形 ，请你在环中找出 'o' 字符出现了偶数次最长子字符串的长度。
     *
     * @param input
     * @return
     */
    public static int maxLength(String input) {
        final int length = input.length();

        // 'o'出现次数
        int oCount = 0;
        // 上一个'o'位置
        int lastPos = -1;
        int firstPos = -1;
        // 相邻两个'o'的最近距离
        int minDistance = Integer.MAX_VALUE;
        for (int i = 0; i < length; i++) {
            if (input.charAt(i) == 'o') {
                if (oCount == 0) {
                    firstPos = i;
                }
                oCount++;
                if (oCount > 1) {
                    // 计算相邻两个'o'的最近距离
                    int distance = i - lastPos;
                    minDistance = Math.min(distance, minDistance);
                }
                lastPos = i;
            }
        }

        if (oCount % 2 == 0) {
            return length;
        }

        // 环形，需要计算下尾到首两个'o'的距离
        minDistance = Math.min(minDistance, length + firstPos - lastPos);

        return length - minDistance;
    }
}
