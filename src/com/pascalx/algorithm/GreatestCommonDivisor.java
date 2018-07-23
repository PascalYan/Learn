package com.pascalx.algorithm;

/**
 * 最大公约数
 *
 * @author yanghui10
 * @date 2018/7/23.
 */
public class GreatestCommonDivisor {


    /**
     * 辗转相除法求最大公约数，最简单的递归
     *
     * @param a
     * @param b
     * @return
     */
    public static int gcd(int a, int b) {

        int m = a > b ? a : b;
        int n = a > b ? b : a;

        int y = 0;
        if ((y = m % n) == 0) {
            return n;
        } else {
            return gcd(n, y);
        }
    }

    public static void main(String[] args) {
        System.out.println(gcd(5, 7));
        System.out.println(gcd(2, 6));
    }
}
