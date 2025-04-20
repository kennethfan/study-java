package io.github.kennethfan.od.star1;

import java.util.Stack;

public class N7 {

    public static void main(String[] args) {
        String expression = "1#2$3#4$5#6#7#8$9";
        System.out.println("expression=" + expression);
        System.out.println("result=" + calculate(expression));

        expression = "1#2";
        System.out.println("expression=" + expression);
        System.out.println("result=" + calculate(expression));

        expression = "12$3";
        System.out.println("expression=" + expression);
        System.out.println("result=" + calculate(expression));

        expression = "30$4";
        System.out.println("expression=" + expression);
        System.out.println("result=" + calculate(expression));

        expression = "5#6";
        System.out.println("expression=" + expression);
        System.out.println("result=" + calculate(expression));

        expression = "40#7";
        System.out.println("expression=" + expression);
        System.out.println("result=" + calculate(expression));

        expression = "183#8";
        System.out.println("expression=" + expression);
        System.out.println("result=" + calculate(expression));

        expression = "67$758";
        System.out.println("expression=" + expression);
        System.out.println("result=" + calculate(expression));

        expression = "895$9";
        System.out.println("expression=" + expression);
        System.out.println("result=" + calculate(expression));
    }

    /**
     * 已知火星人使用的运算符为#、$，其与地球人的等价公式如下：
     * x#y = 4*x+3*y+2
     * x$y = 2*x+y+3
     * 1、其中x、y是无符号整数
     * 2、地球人公式按C语言规则计算
     * 3、火星人公式中，#的优先级高于$，相同的运算符，按从左到右的顺序计算
     * 现有一段火星人的字符串报文，请你来翻译并计算结果。
     *
     * @param input
     * @return
     */
    public static int calculate(String input) {
        if (input == null || input.length() == 0) {
            throw new IllegalArgumentException("input is null or empty");
        }

        char[] chars = input.toCharArray();


        Stack<Character> operators = new Stack<>();
        Stack<Integer> operands = new Stack<>();

        final int length = chars.length;
        int num = 0;
        char ch;
        for (int i = 0; i < length; i++) {
            ch = chars[i];
            if (!valid(ch)) {
                throw new IllegalArgumentException("invalid input");
            }

            // 数字
            if (ch >= '0' && ch <= '9') {
                num = num * 10 + ch - '0';
                continue;
            }

            // 开头和结束必须是数字
            if (i == 0 || i == chars.length - 1) {
                throw new IllegalArgumentException("invalid input");
            }

            // x和操作符必须成对出现
            if (operands.size() != operators.size()) {
                throw new IllegalArgumentException("invalid input");
            }

            if (operands.isEmpty()) {
                operands.push(num);
                operators.push(ch);
                num = 0;
                continue;
            }

            int y = num;
            num = 0;
            // 上个操作符是#
            if (operators.peek() == '#') {
                operands.push(apply(operators.pop(), operands.pop(), y));
                operators.push(ch);
                num = 0;

                continue;
            }

            // 高优先级
            if (ch == '#') {
                operands.push(num);
                operators.push(ch);
                continue;
            }

            if (operands.isEmpty() || operators.isEmpty()) {
                throw new IllegalArgumentException("invalid input");
            }

            operands.push(apply(operators.pop(), operands.pop(), y));
            operators.push(ch);
            num = 0;
        }

        if (operands.isEmpty()) {
            return num;
        }

        return apply(operators.pop(), operands.pop(), num);
    }

    private static boolean valid(char ch) {
        if (ch >= '0' && ch <= '9') {
            return true;
        }

        return ch == '#' || ch == '$';
    }

    private static int apply(char op, int x, int y) {
        if (op == '#') {
            return 4 * x + 3 * y + 2;
        }

        return 2 * x + y + 3;
    }
}
