package io.github.kennethfan.jnr;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class WordMain {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        final int length = in.nextInt();

        String[] words = new String[length];
        String chars = null;

        for (int i = 0; i < length; i++) {
            words[i] = in.next();
        }
        chars = in.next();
        in.close();

        Map<Character, Integer> charCountMap = new HashMap<>();
        char[] chs = chars.toCharArray();
        for (int i = 0; i < chs.length; i++) {
            charCountMap.put(chs[i], charCountMap.getOrDefault(chs[i], 0) + 1);
        }


        int sum = 0;
        for (int i = 0; i < words.length; i ++) {
            sum += match(words[i], new HashMap<>(charCountMap));
        }

        System.out.println(sum);
    }

    private static int match(String word, Map<Character, Integer> charCountMap) {
        char[] chs = word.toCharArray();

        char ch;
        for (int i = 0; i < chs.length; i++) {
            ch = chs[i];
            Integer count = charCountMap.get(ch);
            if (count != null && count > 0) {
                charCountMap.put(ch, count - 1);
                continue;
            }
            count = charCountMap.get('?');
            if (count != null && count > 0) {
                charCountMap.put('?', count - 1);
                continue;
            }
            return 0;
        }

//        System.out.println(word);
        return 1;
    }

    /**
     * 3
     * apple
     * car
     * window
     * welldoneapplec?
     */

//3
//    hello
//            world
//    cloud
//            welldonehoneyr
}
