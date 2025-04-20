package io.github.kennethfan.od.star1;

import java.util.ArrayList;
import java.util.List;

public class N8 {

    public static void main(String[] args) {
        for (int i = 5; i < 100; i++) {
            solution(i);
        }
    }

    private static void solution(int input) {
        List<String> combines = calculate(input);
        if (combines.isEmpty()) {
            System.out.println("No solution for " + input);
            return;
        }

        for (int i = 0; i < combines.size(); i++) {
            System.out.println(input + " = " + combines.get(combines.size() - 1 - i));
        }
    }

    /**
     * 一个整数可以由连续的自然数之和来表示。给定一个整数，计算该整数有几种连续自然数之和的表达式，且打印出每种表达式。
     *
     * @param n
     * @return
     */
    public static List<String> calculate(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("");
        }

        List<String> result = new ArrayList<>();
        int m = 2;
        int s = n - m * (m - 1) / 2;
        do {
            if (s % m == 0) {
                int k = s / m;
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < m; i++) {
                    if (i != 0) {
                        sb.append(" + ");
                    }
                    sb.append(k++);
                }
                result.add(sb.toString());
            }
            m++;
            s = n - m * (m - 1) / 2;
        } while (s > 0);

        return result;
    }
}
