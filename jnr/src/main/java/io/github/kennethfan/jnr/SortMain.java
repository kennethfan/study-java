package io.github.kennethfan.jnr;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class SortMain {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        final int length = in.nextInt();

        List<Pair> heights = new ArrayList<>();
        int[] weights = new int[length];

        for (int i = 0; i < length; i++) {
            heights.add(new Pair(in.nextInt(), i));
        }
        for (int i = 0; i < length; i++) {
            weights[i] = in.nextInt();
        }


        heights.sort(new Comparator<Pair>() {
            @Override
            public int compare(Pair o1, Pair o2) {
                if (o1.getValue() == o2.getValue()) {
                    if( weights[o1.getIndex()] == weights[o2.getIndex()] ) {
                        return 0;
                    }
                    return weights[o1.getIndex()] < weights[o2.getIndex()] ? -1 : 1;
                }

                return o1.getValue() < o2.getValue() ? -1 : 1;
            }
        });

        for (Pair pair : heights) {
            System.out.print(pair.getIndex() + 1);
            System.out.print(" ");
        }

//        // 注意 hasNext 和 hasNextLine 的区别
//        while (in.hasNextInt()) { // 注意 while 处理多个 case
//            int a = in.nextInt();
//            int b = in.nextInt();
//            System.out.println(a + b);
//        }
    }

    static class Pair {
        private int value;
        private int index;

        public Pair(int value, int index) {
            this.value = value;
            this.index = index;
        }

        public int getValue() {
            return value;
        }

        public int getIndex() {
            return index;
        }
    }
}
