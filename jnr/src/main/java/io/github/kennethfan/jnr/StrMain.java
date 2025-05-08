package io.github.kennethfan.jnr;

import java.util.*;

public class StrMain {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        List<Integer> numbers = new ArrayList<>();
        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNextInt()) { // 注意 while 处理多个 case
            numbers.add(in.nextInt());
        }
        if (numbers.size() % 6 != 0) {
            throw new IllegalArgumentException("Invalid input");
        }
        int length = numbers.size() / 2;

        Map<Integer, Set<Integer>> groups = new HashMap<>(length);

        Set<Integer> group = new HashSet<>();
        Integer number;
        for (int i = length; i < numbers.size(); i++) {
            if (i % 3 == 0) {
                group = new HashSet<>();
            }

            number = numbers.get(i);
            group.add(number);
            groups.put(number, group);
        }


        Map<Set<Integer>, List<Set<Integer>>> mapping = new HashMap<>();
        LinkedList<Set<Integer>> segments = new LinkedList<>();
        Set<Integer> seg = new HashSet<>();
        Set<Integer> segGroup = new HashSet<>();
        for (int i = 0; i < length; i++) {
            number = numbers.get(i);
            if (seg.size() == 0) {
                seg.add(number);
                segGroup = groups.get(number);
                continue;
            }

            if (!segGroup.contains(number)) {
                segments.add(seg);
                segGroup = groups.get(number);
                seg = new HashSet<>();
                seg.add(number);

                // 结尾
                if (i == length - 1) {
                    segments.add(seg);
                    mapping.put(group, segments);
                }

                continue;
            }

            seg.add(number);
            if (seg.size() == 3) {
                seg = new HashSet<>();
                continue;
            }

            if (i == length - 1) {
                segments.add(seg);
                mapping.put(group, segments);
            }
        }

        if (segments.size() == 0) {
            System.out.println(0);
            return;
        }

//        组合成2的
        int sum = 0;
        boolean flag = false;
        do {
            LinkedList<Set<Integer>> newSegments = new LinkedList<>();
            int i = 0;
            while (i < segments.size()) {
                Set<Integer> segment = segments.get(i++);
                group = groups.get(segment.stream().findFirst());
                List<Set<Integer>> x = mapping.get(group);
                if (x == null || x.size() == 2) {
                    sum++;
                    // 删除
                    if (x != null) {
                        mapping.remove(group);
                    }

                    if (newSegments.peek() == null) {
                        continue;
                    }

                    if (i == segments.size() - 1) {
                        continue;
                    }
                    Set<Integer> nextSeg = segments.get(i);
                    if (group == groups.get(nextSeg.stream().findFirst())) {
                        newSegments.peek().addAll(nextSeg);
                        flag = true;
                        i++;
                    }
                    continue;
                }

                newSegments.add(segment);
            }
            if (newSegments.size() == 0) {
                System.out.println(sum);
                return;
            }
            segments = newSegments;
        } while (flag);

        sum += newSegments.size() / 3 * 2;

        System.out.println(sum);
    }

    /**
     * 7 9 8 || 5 6 || 4 2 1 || 3 || 10 14 || 15 || 11 || 12 13
     * 7 8 9 4 2 1 3 5 6 15 12 13 11 14 10
     *
     *
     *
     * 1 4 7 2 5 8 3 6 9
     *
     *
     * 1 2 3 4 5 6 7 8 9
     *
     *
     * 1 2 4 7 5 8 3 6 9
     * 1 2 3 4 7 5 8 6 9
     * 1 2 3 4 6 7 5 8 9
     * 1 2 3 4 6 5 7 8 9
     *
     * 1 4 7 10 2 5 8 11 3 6 9 12
     * 1 2 4 7 10 5 8 11 3 6 9 12
     * 1 2 3 4 7 10 5 8 11 6 9 12
     * 1 2 3 4 5 7 10 8 11 6 9 12
     * 1 2 3 4 5 6 7 10 8 11  9 12
     * 1 2 3 4 5 6 7 9 10 8 11  12
     * 1 2 3 4 5 6 7 9  8 10  11  12
     */

}
