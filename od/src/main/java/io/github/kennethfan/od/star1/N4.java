package io.github.kennethfan.od.star1;

public class N4 {

    public static void main(String[] args) {

        String pattern = "abcde";
        String text = "abcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabc";

        System.out.println("text=" + text);
        System.out.println("pattern=" + pattern);
        System.out.println("match=" + match(pattern, text));

        text = "aaaaaaddfdslkajfdlakjbdlkjkldddddd";
        System.out.println("pattern=" + pattern);
        System.out.println("match=" + match(pattern, text));
    }

    /**
     * 输入两个字符串S和L，都只包含英文小写字母。S长度<=100，L长度<=500,000。判定S是否是L的有效字串。
     * 判定规则：S中的每个字符在L中都能找到（可以不连续），且S在Ｌ中字符的前后顺序与S中顺序要保持一致。（例如，S="ace"是L="abcde"的一个子序列且有效字符是a、c、e，而"aec"不是有效子序列，且有效字符只有a、e）
     *
     * @param pattern
     * @param text
     * @return
     */
    public static boolean match(CharSequence pattern, CharSequence text) {
        if (pattern == null || text == null) {
            throw new IllegalArgumentException("pattern and text must not be null");
        }

        if (text.length() < pattern.length()) {
            return false;
        }

        int matchLength = 0;

        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == pattern.charAt(matchLength)) {
                matchLength++;
                if (matchLength == pattern.length()) {
                    return true;
                }
            }
        }

        return false;
    }
}
