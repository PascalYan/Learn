package com.pascalx.algorithm;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author yanghui10
 * @date 2018/7/24.
 */
public class BinaryAdder {


    public static String addBinary(String a, String b) {
        StringBuilder sb = new StringBuilder();
        int i = a.length() - 1;
        int j = b.length() - 1;
        int carry = 0;
        while (i >= 0 || j >= 0) {
            int sum = carry;
            if (i >= 0) {
                sum += a.charAt(i--) - '0';
            }
            if (j >= 0) {
                sum += b.charAt(j--) - '0';
            }
            sb.append(sum % 2);
            carry = sum / 2;
        }
        //如果最终仍然有进位，将进位添加到StringBuilder中
        if (carry != 0) {
            sb.append(carry);
        }
        //反转得到正确结果
        return sb.reverse().toString();
    }



    public static void main(String[] args) {
        System.out.println(addBinary("111111111111", "1"));
    }

}
