package com.pascalx.algorithm;

/**
 * Created by yanghui on 2018/7/20.
 */
public class StringCompress {

    /**
     * @param origin 源字符串
     * @return 压缩后的字符串
     */
    public static String compress(String origin) {
        if (origin == null) throw new NullPointerException();

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < origin.length(); ) {
            char c = origin.charAt(i);
            int count = 1;
            sb.append(c);

            for (int j = i + 1; j < origin.length(); j++) {
                if (c == origin.charAt(j)) {
                    count++;
                } else {
                    break;
                }
            }

            if (count != 1) {
                sb.append(count);
            }
            i += count;
        }
        return sb.toString();
    }

    /**
     * test
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(compress("a"));
        System.out.println(compress(""));
        System.out.println(compress("aa"));
        System.out.println(compress("abc"));
        System.out.println(compress("abcacc"));
        System.out.println(compress("abcaccb"));
        System.out.println(compress("abcaccbb"));
        System.out.println(compress("abcaccbbcbbcbbcb"));
        System.out.println(compress("aabccccaaa"));
    }
}
