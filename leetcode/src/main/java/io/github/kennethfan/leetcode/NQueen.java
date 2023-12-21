package io.github.kennethfan.leetcode;

/**
 * Created by kenneth on 2023/5/30.
 */
public class NQueen {
    private int n;

    public NQueen(int n) {
        this.n = n;
    }

    public void calc() {
        int[] cols = new int[this.n];

        this.doCalc(0, cols);
    }

    /**
     * N皇后计算
     * <p>
     * N皇后特点
     * 1、每一行只有一个元素
     * 2、每一列只有一个元素
     * 3、每条对角线只有一个元素
     * <p>
     * <p>
     * 思路
     * 两个皇后位置分别为(i1, j1)和(i2, j2)
     * 1、如果i1 == i2，则在同一行
     * 2、如果j1 == j2，则在同一列
     * 3、如果i1 + j1 == i2 + j2，则在45°对角线
     * 4、如果i1 - j1 == i2 - j2，则在-45°对角线
     * <p>
     * <p>
     * 同一行不会有多个皇后；所以定义数组cols，索引表示皇后所在行，值表示皇后所在列
     * 第k个皇后所在位置为(k - 1, j)，则cols[0]...cols[k - 2]需要判断思路分析中2、3、4条；第1条天然满足
     *
     * @param i
     * @param cols
     */
    private void doCalc(int i, int[] cols) {
        if (i >= this.n) {
            return;
        }

        for (int j = 0; j < this.n; j++) {
            if (!doCheck(i, j, cols)) {
                continue;
            }

            cols[i] = j;
            if (i == this.n - 1) {
                this.draw(cols);
                return;
            }

            this.doCalc(i + 1, cols);
        }
    }

    private void draw(int cols[]) {
        final int length = cols.length;

        for (int i = 0; i < length; i++) {
            int col = cols[i];
            for (int j = 0; j < length; j++) {
                System.out.print(j == col ? "◆" : "◇");
            }
            System.out.println();
        }

        System.out.println();
        System.out.println();
    }

    private boolean doCheck(int i, int j, int[] cols) {
        for (int index = 0; index < i; index++) {
            // 同一列
            if (cols[index] == j) {
                return false;
            }

            //  45°
            if (index + cols[index] == i + j) {
                return false;
            }

            // -45°
            if (index - cols[index] == i - j) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        for (int i = 1; i <= 10; i++) {
            new NQueen(i).calc();
        }
    }
}
