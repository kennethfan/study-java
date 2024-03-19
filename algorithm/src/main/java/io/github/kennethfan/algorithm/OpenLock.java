package io.github.kennethfan.algorithm;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

@Slf4j
public class OpenLock {

    public static void main(String[] args) {
        Set<String> deadends = Set.of("0201", "0101", "0102", "1212", "2002");
        String target = "0202";

        log.info("minLockStep of target {} with deadends {} is {}", target, deadends, minLockStep(deadends, target));
    }

    /**
     * 你有一个带有四个圆形拨轮的转盘锁。每个拨轮都有10个数字：0，1，3，4，5，6，7，8，9
     * 每个拨轮可以自由旋转：例如把9，变为0, 0变为9
     * 每次选转都只能旋转一个拨轮的一位数宇。
     * 0000
     * 一个代表四个拨轮的数字的字符串。
     * 列表 deadends 包含了一组死亡数字，一旦拨轮的数字和列表里的任何一个元素相同，这个锁将会被永久
     * 锁定，无法再被旋转。
     * 宇符串 target 代表可以解锁的数宇，你需要给出最小的旋转次数，如果无论如何不能解锁，返回-1。
     * 11:
     * 输入：deadends = ["0201","0101","0102","1212","2002"], target = “0202"
     * 输出：6
     * 解释：
     * 可能的移动序列为 0000 -> 1000 -> 1100 -> 1200 -> 1201 -> 1202 -> 0202
     * 注意
     * 0000 -> 0001 -> 0002 -> 0202这样的序列是不能解锁的
     * 因为当拨动到0102 时这个锁就会被锁定，
     *
     * @param deadends
     * @param target
     */
    private static int minLockStep(Set<String> deadends, String target) {

        String init = "0000";
        if (init.equals(target)) {
            return 0;
        }

        if (deadends.contains(target)) {
            return -1;
        }

        Set<String> visited = new HashSet<>();
        visited.add(init);
        Queue<String> queue = new LinkedList<>();
        queue.offer(init);
        int step = 1;

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            for (int i = 0; i < levelSize; i++) {
                String cur = queue.poll();
                for (int charIndex = 0; charIndex < cur.length(); charIndex++) {
                    String upNext = up(cur, charIndex);
                    if (upNext.equals(target)) {
                        return step;
                    }
                    if (!visited.contains(upNext) && !deadends.contains(upNext)) {
                        visited.add(upNext);
                        queue.offer(upNext);
                    }

                    String downNext = down(cur, charIndex);
                    if (downNext.equals(target)) {
                        return step;
                    }
                    if (!visited.contains(downNext) && !deadends.contains(downNext)) {
                        visited.add(downNext);
                        queue.offer(downNext);
                    }
                }
            }
            step++;
        }

        return -1;
    }


    private static String up(String input, int index) {
        char[] chArr = input.toCharArray();
        char c = chArr[index];
        if (c == '9') {
            c = '0';
        } else {
            c += 1;
        }
        chArr[index] = c;

        return new String(chArr);
    }

    private static String down(String input, int index) {
        char[] chArr = input.toCharArray();
        char c = chArr[index];
        if (c == '0') {
            c = '9';
        } else {
            c -= 1;
        }
        chArr[index] = c;

        return new String(chArr);
    }

}
