package io.github.kennethfan.algorithm;

import java.util.ArrayList;
import java.util.List;

public class NQueen {

    public static void main(String[] args) {
        for (int i = 1; i <= 8; i++) {
            System.out.println(i + " queen begin ");
            print(queen(i));
            System.out.println(i + " queen end ");
        }
    }

    private static void print(List<List<Integer>> result) {
        for (List<Integer> branch : result) {
            final int n = branch.size();
            for (int row = 0; row < n; row++) {
                final int col = branch.get(row);
                for (int i = 0; i < n; i++) {
                    System.out.print(i == col ? "1 " : "0 ");
                }
                System.out.println("");
            }
            System.out.println("");
            System.out.println("");
        }
    }

    /**
     * 给定一个N*N的棋盘，在棋盘上放N个皇后，两个皇后不能在同一行，同一列，也不能在同一对角线上
     *
     * @param n
     * @return
     */
    private static List<List<Integer>> queen(int n) {
        if (n <= 0) {
            return new ArrayList<>();
        }

        return findColumns(n, new ArrayList<>());
    }

    private static List<List<Integer>> findColumns(int n, List<Integer> cols) {
        List<List<Integer>> result = new ArrayList<>();
        if (n == cols.size()) {
            result.add(cols);
            return result;
        }

        for (int i = 0; i < n; i++) {
            if (conflict(i, cols)) {
                continue;
            }

            List<Integer> cs = new ArrayList<>(cols);
            cs.add(i);
            result.addAll(findColumns(n, cs));
        }

        return result;
    }

    private static boolean conflict(int col, List<Integer> cols) {
        int row = cols.size();

        for (int i = 0; i < cols.size(); i++) {
            // 同一列
            int j = cols.get(i);
            if (j == col) {
                return true;
            }
            // 45度方向
            if (j - i == col - row) {
                return true;
            }
            if (i + j == row + col) {
                return true;
            }
        }

        return false;
    }
}
